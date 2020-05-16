package person.rulo.kafka.learning.common.pojo;

import java.io.Serializable;

/**
 * @Author rulo
 * @Date 2020/5/9 20:11
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "姓名: " + name + ", 年龄: " + age;
    }
}
