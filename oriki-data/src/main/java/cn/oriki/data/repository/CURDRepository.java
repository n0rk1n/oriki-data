package cn.oriki.data.repository;

import java.io.Serializable;

public interface CURDRepository<T, ID extends Serializable> extends Repository<T, ID> {
}