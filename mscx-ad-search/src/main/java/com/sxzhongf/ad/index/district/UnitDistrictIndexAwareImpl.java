package com.sxzhongf.ad.index.district;

import com.sxzhongf.ad.index.IIndexAware;
import com.sxzhongf.ad.search.vo.feature.DistrictFeature;
import com.sxzhongf.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * UnitDistrictIndexAwareImpl for 推广单元<->地域索引实现类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/24
 */
@Slf4j
@Component
public class UnitDistrictIndexAwareImpl implements IIndexAware<String, Set<Long>> {

    // key 值为 ${province}-${city}拼接串
    private static Map<String, Set<Long>> districtRelationUnitsMap;
    private static Map<Long, Set<String>> unitRelationDistrictsMap;

    static {
        districtRelationUnitsMap = new ConcurrentHashMap<>();
        unitRelationDistrictsMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return districtRelationUnitsMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitDistrictIndexAwareImpl::unitRelationDistrictsMap, before add :{}", unitRelationDistrictsMap);

        //获取地域维度对应的推广单元id Set
        Set<Long> unitIdSet = CommonUtils.getOrCreate(
                key, districtRelationUnitsMap, ConcurrentSkipListSet::new
        );
        unitIdSet.addAll(value);

        // 循环添加 每个兴趣标签对应的 关键词Set
        for (Long unitId : value) {
            Set<String> districtSet = CommonUtils.getOrCreate(
                    unitId, unitRelationDistrictsMap, ConcurrentSkipListSet::new
            );
            districtSet.add(key);
        }
        log.info("UnitDistrictIndexAwareImpl::unitRelationDistrictsMap, after add :{}", unitRelationDistrictsMap);
    }


    @Override
    public void update(String key, Set<Long> value) {
        log.error("UnitDistrictIndexAwareImpl::unitRelationDistrictsMap, can not support update,因为会遍历Set，直接删除后重新添加");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitDistrictIndexAwareImpl::before delete:{}", unitRelationDistrictsMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(
                key, districtRelationUnitsMap, ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getOrCreate(unitId, unitRelationDistrictsMap, ConcurrentSkipListSet::new);
            districts.remove(key);
        }

        log.info("UnitDistrictIndexAwareImpl::after delete:{}", unitRelationDistrictsMap);
    }

    /**
     * 查询参数中是否存在匹配的广告地域信息
     */
    public boolean match(Long adUnitId, List<DistrictFeature.ProvinceAndCity> districts) {
        if (unitRelationDistrictsMap.containsKey(adUnitId) && CollectionUtils.isNotEmpty(unitRelationDistrictsMap.get(adUnitId))) {
            Set<String> unitDistricts = unitRelationDistrictsMap.get(adUnitId);

            //id为province+city拼接
            List<String> targetDistricts = districts.stream()
                                                    .map(d -> {
                                                        return com.sxzhongf.ad.common.utils.CommonUtils.stringConcat(
                                                                d.getProvince(), d.getCity()
                                                        );
                                                    }).collect(Collectors.toList());
            return CollectionUtils.isSubCollection(targetDistricts, unitDistricts);
        }

        return false;
    }
}
