////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import ltd.qubit.commons.model.Address;
import ltd.qubit.commons.model.Contact;
import ltd.qubit.commons.model.Device;
import ltd.qubit.commons.model.Info;
import ltd.qubit.commons.model.InfoWithEntity;
import ltd.qubit.commons.model.Organization;
import ltd.qubit.commons.model.PersonInfo;
import ltd.qubit.commons.model.State;
import ltd.qubit.commons.model.StatefulInfo;

import org.junit.jupiter.api.Test;

import java.sql.SQLSyntaxErrorException;
import java.time.Instant;

import static ltd.qubit.commons.util.LogicRelation.AND;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CriterionBuilderTest {

  @Test
  public void testBuildDevice() throws SQLSyntaxErrorException {
    final Criterion<Device> criterion =
        new ComposedCriterionBuilder<>(Device.class, AND)
            .equal(Device::getApp, StatefulInfo::getId, 10001L)
            .like(Device::getName, "device-")
            .equal(Device::getModel, "model-1")
            .equal(Device::getVersion, "v1.0")
            .equal(Device::getOsType, "ANDROID")
            .equal(Device::getOsName, "Google Android")
            .equal(Device::getOsVersion, "v8.1.0")
            .equal(Device::getOwner, PersonInfo::getId, 20002L)
            .equal(Device::getDeployAddress, Address::getStreet, Info::getId, 1011L)
            .equal(Device::getState, State.DISABLED)
            .greaterEqual(Device::getRegisterTime, Instant.parse("2022-01-02T21:23:33Z"))
            .lessEqual(Device::getRegisterTime, Instant.parse("2022-03-02T21:23:33Z"))
            .greaterEqual(Device::getLastStartupTime, Instant.parse("2022-02-01T00:00:00Z"))
            .lessEqual(Device::getLastStartupTime, Instant.parse("2022-07-01T00:00:00Z"))
            .greaterEqual(Device::getLastHeartbeatTime, Instant.parse("2022-05-01T00:00:00Z"))
            .lessEqual(Device::getLastHeartbeatTime, Instant.parse("2022-06-01T00:00:00Z"))
            .requireNotNull(Device::getDeleteTime, true)
            .greaterEqual(Device::getCreateTime, null)
            .lessEqual(Device::getCreateTime, null)
            .greaterEqual(Device::getModifyTime, null)
            .lessEqual(Device::getModifyTime, Instant.parse("2022-06-01T00:00:00Z"))
            .greaterEqual(Device::getDeleteTime, Instant.parse("2022-02-01T00:00:00Z"))
            .lessEqual(Device::getDeleteTime, null)
            .build();
    final String sql = criterion.toSql();
    final String expected = "(`app_id` = 10001) "
        + "AND (`name` LIKE '%device-%') "
        + "AND (`model` = 'model-1') "
        + "AND (`version` = 'v1.0') "
        + "AND (`os_type` = 'ANDROID') "
        + "AND (`os_name` = 'Google Android') "
        + "AND (`os_version` = 'v8.1.0') "
        + "AND (`owner_id` = 20002) "
        + "AND (`deploy_address_street_id` = 1011) "
        + "AND (`state` = 'DISABLED') "
        + "AND (`register_time` >= '2022-01-02T21:23:33Z') "
        + "AND (`register_time` <= '2022-03-02T21:23:33Z') "
        + "AND (`last_startup_time` >= '2022-02-01T00:00:00Z') "
        + "AND (`last_startup_time` <= '2022-07-01T00:00:00Z') "
        + "AND (`last_heartbeat_time` >= '2022-05-01T00:00:00Z') "
        + "AND (`last_heartbeat_time` <= '2022-06-01T00:00:00Z') "
        + "AND (`delete_time` IS NOT NULL) "
        + "AND (`modify_time` <= '2022-06-01T00:00:00Z') "
        + "AND (`delete_time` >= '2022-02-01T00:00:00Z')";
    assertEquals(expected, sql);
  }

  @Test
  public void testBuildOrganization() throws SQLSyntaxErrorException {
    final Criterion<Organization> criterion =
        new ComposedCriterionBuilder<>(Organization.class, AND)
            .like(Organization::getName, "org-")
            .equal(Organization::getCategory, InfoWithEntity::getId, null)
            .equal(Organization::getCategory, InfoWithEntity::getCode, "cat-1")
            .equal(Organization::getParent, StatefulInfo::getCode, "org-2")
            .equal(Organization::getContact, Contact::getAddress, Address::getCountry, Info::getCode, "CN")
            .equal(Organization::getContact, Contact::getAddress, Address::getProvince, Info::getName, "江苏")
            .equal(Organization::getContact, Contact::getAddress, Address::getCity, Info::getCode, "Nanjing")
            .equal(Organization::getContact, Contact::getAddress, Address::getDistrict, Info::getName, "秦淮区")
            .equal(Organization::getContact, Contact::getAddress, Address::getStreet, Info::getName, "五老村街道")
            .equal(Organization::getState, State.NORMAL)
            .requireNotNull(Organization::getDeleteTime, false)
            .build();
    final String sql = criterion.toSql();
    final String expected = "(`name` LIKE '%org-%') "
        + "AND (`category_code` = 'cat-1') "
        + "AND (`parent_code` = 'org-2') "
        + "AND (`contact_address_country_code` = 'CN') "
        + "AND (`contact_address_province_name` = '江苏') "
        + "AND (`contact_address_city_code` = 'Nanjing') "
        + "AND (`contact_address_district_name` = '秦淮区') "
        + "AND (`contact_address_street_name` = '五老村街道') "
        + "AND (`state` = 'NORMAL') "
        + "AND (`delete_time` IS NULL)";
    assertEquals(expected, sql);
  }
}
