package org.example.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The type Employee.
 */
@Entity
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is mandatory")
  @Size(max = 100, message = "Name should not exceed 100 characters")
  private String name;

  @NotBlank(message = "Position is mandatory")
  private String position;

  @NotBlank(message = "Mobile number is mandatory")
  @Size(max = 10, message = "Mobile number should not exceed 10 characters")
  private String mobileNo;

  @NotBlank(message = "Address is mandatory")
  @Size(max = 255, message = "Address should not exceed 255 characters")
  private String address;


  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets position.
   *
   * @return the position
   */
  public String getPosition() {
    return position;
  }

  /**
   * Sets position.
   *
   * @param position the position
   */
  public void setPosition(String position) {
    this.position = position;
  }

  /**
   * Gets mobile no.
   *
   * @return the mobile no
   */
  public String getMobileNo() {
    return mobileNo;
  }

  /**
   * Sets mobile no.
   *
   * @param mobileNo the mobile no
   */
  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  /**
   * Gets address.
   *
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Sets address.
   *
   * @param address the address
   */
  public void setAddress(String address) {
    this.address = address;
  }
}
