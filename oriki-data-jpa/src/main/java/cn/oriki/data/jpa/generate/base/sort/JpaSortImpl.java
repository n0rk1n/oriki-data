package cn.oriki.data.jpa.generate.base.sort;

import cn.oriki.commons.utils.collection.Collections;
import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.sort.AbstractSort;
import cn.oriki.data.generate.base.sort.entiy.OrderEntity;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;

import java.util.List;
import java.util.stream.Collectors;

public class JpaSortImpl extends AbstractSort {

    private static final String ORDER_BY_KEY_WORD = " ORDER BY ";

    @Override
    public GenerateResult generate() throws GenerateException {
        // GOAL:
        //      order by key1 asc , key2 desc
        List<OrderEntity> orders = getOrders();

        List<String> list = orders.stream().map((orderEntity) ->
                orderEntity.getOrder() + orderEntity.getDirection().getOrder()
        ).collect(Collectors.toList());

        String stringBuilder = ORDER_BY_KEY_WORD +
                Collections.join(list, Generate.COMMA);

        GenerateResult generateResult = new GenerateResult();
        generateResult.setGenerateResult(stringBuilder);
        return generateResult;
    }

}
