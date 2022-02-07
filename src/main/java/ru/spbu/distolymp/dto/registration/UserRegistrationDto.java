package ru.spbu.distolymp.dto.registration;

import javax.validation.constraints.NotNull;

/**
 * @author Daria Usova
 */
public class UserRegistrationDto {

    @NotNull
    private Long countryId;
    private String newCountryName;
    private Long regionId;
    @NotNull
    private String townName;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getNewCountryName() {
        return newCountryName;
    }

    public void setNewCountryName(String newCountryName) {
        this.newCountryName = newCountryName;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

}
