package com.dream.payroll.service.impl;

import com.dream.payroll.entity.Address;
import com.dream.payroll.mapper.AddressMapper;
import com.dream.payroll.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址 服务实现类
 * </p>
 *
 * @author wyn
 * @since 2020-05-06
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

}
