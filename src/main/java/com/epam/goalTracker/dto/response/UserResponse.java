//package com.epam.goalTracker.dto.response;
//
//import com.epam.sportbuddies.entity.enums.Access;
//import com.epam.sportbuddies.entity.enums.Gender;
//import com.epam.sportbuddies.entity.enums.Role;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class UserResponse implements Serializable {
//
//    private Long id;
//    private String firstName;
//    private String secondName;
//    private String email;
//    private Role role;
//    private Gender gender;
//    private String dateOfBirthday;
//    private AddressResponse address;
//    private String fileName;
//    private PersonInfoResponse personInfo;
//    private Access access;
//    private Access trainingResultAccess;
//
//    public UserResponse() {
//    }
//
//    public UserResponse(String firstName, String secondName, String email, Gender gender, String dateOfBirthday, AddressResponse address) {
//        this.firstName = firstName;
//        this.secondName = secondName;
//        this.email = email;
//        this.gender = gender;
//        this.dateOfBirthday = dateOfBirthday;
//        this.address = address;
//    }
//
//    public Gender getGender() {
//        return gender;
//    }
//
//    public void setGender(Gender gender) {
//        this.gender = gender;
//    }
//
//    public String getDateOfBirthday() {
//        return dateOfBirthday;
//    }
//
//    public void setDateOfBirthday(String dateOfBirthday) {
//        this.dateOfBirthday = dateOfBirthday;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getSecondName() {
//        return secondName;
//    }
//
//    public void setSecondName(String secondName) {
//        this.secondName = secondName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public AddressResponse getAddress() {
//        return address;
//    }
//
//    public void setAddress(AddressResponse address) {
//        this.address = address;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public PersonInfoResponse getPersonInfo() {
//        return personInfo;
//    }
//
//    public void setPersonInfo(PersonInfoResponse personInfo) {
//        this.personInfo = personInfo;
//    }
//
//    public Access getAccess() {
//        return access;
//    }
//
//    public void setAccess(Access access) {
//        this.access = access;
//    }
//
//    public Access getTrainingResultAccess() {
//        return trainingResultAccess;
//    }
//
//    public void setTrainingResultAccess(Access trainingResultAccess) {
//        this.trainingResultAccess = trainingResultAccess;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        PersonResponse that = (PersonResponse) o;
//        return Objects.equals(id, that.id) &&
//                Objects.equals(firstName, that.firstName) &&
//                Objects.equals(secondName, that.secondName) &&
//                Objects.equals(email, that.email) &&
//                role == that.role &&
//                gender == that.gender &&
//                Objects.equals(dateOfBirthday, that.dateOfBirthday) &&
//                Objects.equals(address, that.address) &&
//                Objects.equals(fileName, that.fileName) &&
//                Objects.equals(personInfo, that.personInfo) &&
//                access == that.access;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, firstName, secondName, email, role, gender, dateOfBirthday, address, fileName, personInfo, access);
//    }
//
//    @Override
//    public String toString() {
//        return "PersonResponse{" +
//                "id=" + id +
//                ", firstName='" + firstName + '\'' +
//                ", secondName='" + secondName + '\'' +
//                ", email='" + email + '\'' +
//                ", role=" + role +
//                ", gender=" + gender +
//                ", dateOfBirthday='" + dateOfBirthday + '\'' +
//                ", address=" + address +
//                ", fileName='" + fileName + '\'' +
//                ", personInfo=" + personInfo +
//                ", access=" + access +
//                '}';
//    }
//}
