package com.websocket.demo.repository;

import com.websocket.demo.dto.DemoInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoInfoRepository extends CrudRepository<DemoInfo,Long>{

}
