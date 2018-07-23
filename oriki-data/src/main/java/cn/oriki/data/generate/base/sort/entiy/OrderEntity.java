package cn.oriki.data.generate.base.sort.entiy;

import cn.oriki.data.generate.base.sort.enumeration.Direction;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderEntity {

    private String order;
    private Direction direction;

}
