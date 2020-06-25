package repository_2.domain;

import repository_2.domain.validators.ValidatorException;

public class Client extends BaseEntity<Long>{
    private String firstName;
    private String lastName;
    private int age;

    public Client() {
    }

    public Client(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    /**
     * Get First name.
     *
     * @return the client's firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set First name.
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get Last name.
     *
     * @return the client's lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * SetLastName.
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get Age.
     *
     * @return the client's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Set Age.
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * toString
     *
     * @return the client in String format
     */
    @Override
    public String toString() {
        return "Client{ " +
                "First Name=" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                ", Age=" + age +
                "} " + super.toString();
    }
}

