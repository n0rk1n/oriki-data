package cn.oriki.data.generate.curd.delete;

import cn.oriki.data.generate.Generate;

public interface Delete extends Generate {

    // 删除所有
    void deleteAll();

    // 其余删除方式条件遵循 Where

}
