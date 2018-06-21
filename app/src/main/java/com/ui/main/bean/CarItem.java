package com.ui.main.bean;

public class CarItem {


    /**
     * ID : 85
     * Contacts : 都放假
     * Phone : 123456789
     * Quantity : 1
     * LicensePlate : 京JI1237
     * CarModel : 很惨
     * Locality : 北京市朝阳区东五环大黄庄苗圃35号 北京市朝阳区绿源学校
     * SourceState : 1
     */

    private int ID;
    private String Contacts;
    private String Phone;
    private String Quantity;
    private String LicensePlate;
    private String CarModel;
    private String Locality;
    private int SourceState;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContacts() {
        return Contacts;
    }

    public void setContacts(String Contacts) {
        this.Contacts = Contacts;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getLicensePlate() {
        return LicensePlate;
    }

    public void setLicensePlate(String LicensePlate) {
        this.LicensePlate = LicensePlate;
    }

    public String getCarModel() {
        return CarModel;
    }

    public void setCarModel(String CarModel) {
        this.CarModel = CarModel;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String Locality) {
        this.Locality = Locality;
    }

    public int getSourceState() {
        return SourceState;
    }

    public void setSourceState(int SourceState) {
        this.SourceState = SourceState;
    }

    @Override
    public String toString() {
        return "CarItem{" +
                "ID=" + ID +
                ", Contacts='" + Contacts + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", LicensePlate='" + LicensePlate + '\'' +
                ", CarModel='" + CarModel + '\'' +
                ", Locality='" + Locality + '\'' +
                ", SourceState=" + SourceState +
                '}';
    }
}
