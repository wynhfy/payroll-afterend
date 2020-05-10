package com.dream.payroll.service.impl;

import com.dream.payroll.entity.Address;
import com.dream.payroll.entity.Department;
import com.dream.payroll.entity.Employee;
import com.dream.payroll.entity.vo.EmployeeVo;
import com.dream.payroll.exception.PayRollException;
import com.dream.payroll.mapper.EmployeeMapper;
import com.dream.payroll.service.AddressService;
import com.dream.payroll.service.DepartmentService;
import com.dream.payroll.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工 服务实现类
 * </p>
 *
 * @author wyn
 * @since 2020-05-06
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private AddressService addressService;
    @Autowired
    private DepartmentService departmentService;

    @Override
    public boolean saveEmployeeAndAddress(EmployeeVo employeeVo) {
        Employee employee=new Employee();
        BeanUtils.copyProperties(employeeVo,employee);
        boolean flag=this.save(employee);
        if(!flag){
            throw  new PayRollException(20001,"添加员工失败");
        }
        Address address=new Address();
        BeanUtils.copyProperties(employeeVo,address);
        address.setId(employee.getId());
        flag=addressService.save(address);
        if(!flag){
            throw new PayRollException(20001,"添加员工住址信息失败");
        }
        String deptId=employeeVo.getDeptId();
        Department department=departmentService.getById(deptId);
        department.setTotalNum(department.getTotalNum()+1);
        departmentService.updateById(department);
        return true;
    }

    @Override
    public EmployeeVo getEmployeeVoById(String id) {
        EmployeeVo employeeVo=new EmployeeVo();
        Employee employee=this.getById(id);
        BeanUtils.copyProperties(employee,employeeVo);
        Address address=addressService.getById(id);
        BeanUtils.copyProperties(address,employeeVo);
        return employeeVo;
    }

    @Override
    public void updateEmployeeAndAddress(EmployeeVo employeeVo) {
        Employee employee=new Employee();
        BeanUtils.copyProperties(employeeVo,employee);
        boolean flag = this.updateById(employee);
        if(!flag){
            throw new PayRollException(20001,"更新员工信息失败");
        }
        Address address=new Address();
        BeanUtils.copyProperties(employeeVo,address);
        flag=addressService.updateById(address);
        if(!flag){
            throw new PayRollException(20001,"更新员工住址信息失败");
        }
    }

    @Override
    public void removeEmployeeAndAddress(String id) {
        addressService.removeById(id);
        Employee employee=this.getById(id);
        String deptId=employee.getDeptId();
        Department department=departmentService.getById(deptId);
        department.setTotalNum(department.getTotalNum()-1);
        departmentService.updateById(department);
        int row=baseMapper.deleteById(id);
        if(row==0){
            throw  new PayRollException(20001,"删除员工信息失败");
        }
    }
}
