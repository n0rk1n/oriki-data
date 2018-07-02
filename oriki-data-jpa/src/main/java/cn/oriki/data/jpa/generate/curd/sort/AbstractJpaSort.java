package cn.oriki.data.jpa.generate.curd.sort;

import cn.oriki.commons.utils.collection.Collections;
import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.curd.sort.AbstractSort;
import cn.oriki.data.generate.curd.sort.entiy.OrderEntity;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractJpaSort extends AbstractSort {

    private static final String ORDER_BY_KEY_WORD = " ORDER BY ";

    @Override
    public GenerateResult generate() throws GenerateException {
        // GOAL:
        //      order by key1 asc , key2 desc
        List<OrderEntity> orders = getOrders();

        List<String> list = orders.stream().map((orderEntity) ->
                orderEntity.getOrder() + orderEntity.getDirection().getOrder()
        ).collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ORDER_BY_KEY_WORD);
        stringBuilder.append(Collections.join(list, Generate.COMMA));

        GenerateResult generateResult = new GenerateResult();
        generateResult.setGenerateResult(stringBuilder.toString());
        return generateResult;
    }

}
