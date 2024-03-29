package com.dz.springboard.service;

import com.dz.springboard.entity.Address;

import java.util.List;

public interface IAddressService {
    void addNewAddress(Integer uid, String username, Address address);

    Address ByUid(Integer uid);

    List<Address> getByUid(Integer uid);

    void setDefault(Integer aid, Integer uid, String username);

    void delete(Integer aid, Integer uid, String username);

    Address getByAid(Integer aid, Integer uid);


}
