package guru.mque.alfappcloud;

public class User {

    public String id, range, phone, name, grade, group, section, time;

    public User(String id, String range, String phone, String name, String grade, String group, String section, String time) {

        this.id = id;
        this.range = range;
        this.phone = phone;
        this.name = name;

        this.grade = grade;
        this.group = group;
        this.section = section;
        this.time = time;
    }


    public User(String id, String range, String phone, String name, String lastName, String grade, String group) {
        this.id = id;
        this.range = range;
        this.phone = phone;

        this.grade = grade;
        this.group = group;
    }

    public User(String id, String range) {
        this.id = id;
        this.range = range;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
