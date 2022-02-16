package com.codegym.controller;

import com.codegym.model.Contact;
import com.codegym.view.ContactMenu;
import com.codegym.model.Validate;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactManagement {
    private ArrayList<Contact> contactList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Validate validate = new Validate();
    public static final String PATH_NAME_CONTACT = "contactLists.csv";

    public ContactManagement() {
        if (new File(PATH_NAME_CONTACT).length() == 0) {
            this.contactList = new ArrayList<>();
        } else {
            this.contactList = readFile(PATH_NAME_CONTACT);
        }
    }
    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public String getGender(int choice) {
        String gender = "";
        switch (choice) {
            case 1:
                gender = "Male";
                break;
            case 2:
                gender = "Female";
                break;
            case 3:
                gender = "Other";
                break;
        }
        return gender;
    }

    public void addContact() {
        System.out.println("Mời bạn nhập thông tin:");
        System.out.println("--------------------");
        String phoneNumber = enterPhoneNumber();
        System.out.println("▹ Nhập tên nhóm:");
        String group = scanner.nextLine();
        System.out.println("▹ Nhập họ tên:");
        String name = scanner.nextLine();
        System.out.println("▹ Nhập giới tính:");
        System.out.println("▹ 1. Male");
        System.out.println("▹ 2. Female");
        System.out.println("▹ 3. Other");
        int gender = Integer.parseInt(scanner.nextLine());
        if (getGender(gender).equals("")) {
            System.out.println("Nhập sai lựa chọn giới tính, mời nhập lại !");
            return;
        }
        System.out.println("▹ Nhập địa chỉ:");
        String address = scanner.nextLine();
        System.out.println("▹ Nhập ngày sinh(dd-mm-yyyy):");
        String date = scanner.nextLine();
        LocalDate dateOfBirth = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-LL-yyyy"));
        String email = enterEmail();
        for (Contact phone : contactList) {
            if (phone.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("Số điện thoại bị trùng, mời kiểm tra lại !");
                return;
            }
        }
        Contact contact = new Contact(phoneNumber, group, name, getGender(gender), address, dateOfBirth, email);
        contactList.add(contact);
        System.out.println("Thêm " + phoneNumber + " vào danh bạ thành công !");
        System.out.println("--------------------");
    }

    public void updateContact(String phoneNumber) {
        Contact editContact = null;
        for (Contact contact : contactList) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                editContact = contact;
            }
        }
        if (editContact != null) {
            int index = contactList.indexOf(editContact);
            System.out.println("▹ Nhập tên nhóm mới:");
            editContact.setGroup(scanner.nextLine());
            System.out.println("▹ Nhập họ tên mới:");
            editContact.setName(scanner.nextLine());
            System.out.println("▹ Nhập giới tính mới:");
            System.out.println("▹ 1. Male");
            System.out.println("▹ 2. Female");
            System.out.println("▹ 3. Other");
            int gender = Integer.parseInt(scanner.nextLine());
            editContact.setGender(getGender(gender));
            System.out.println("▹ Nhập địa chỉ mới:");
            editContact.setAddress(scanner.nextLine());
            System.out.println("▹ Nhập ngày sinh mới(dd-mm-yyyy):");
            String date = scanner.nextLine();
            LocalDate dateOfBirth = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-LL-yyyy"));
            editContact.setDateOfBirth(dateOfBirth);
            editContact.setEmail(enterEmail());
            contactList.set(index, editContact);
            System.out.println("Cập nhật thành công !");
        } else {
            System.out.println("Không tìm được danh bạ với số điện thoại trên !");
        }
    }

    public void deleteContact(String phoneNumber) {
        Contact deleteContact = null;
        for (Contact contact : contactList) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                deleteContact = contact;
            }
        }
        if (deleteContact != null) {
            System.out.println("▹ Nhập xác nhận:");
            System.out.println("▹ Y: Xóa");
            System.out.println("▹ Ký tự khác: Thoát");
            String confirm = scanner.next();
            if (confirm.equalsIgnoreCase("y")) {
                contactList.remove(deleteContact);
                System.out.println("Xóa " + phoneNumber + " thành công !");
            }
        } else {
            System.out.println("Không tìm được danh bạ với số điện thoại trên !");
        }
    }

    public void searchContactByNameOrPhone(String keyword) {
        ArrayList<Contact> contacts = new ArrayList<>();
        for (Contact contact : contactList) {
            if (validate.validatePhoneOrName(keyword, contact.getPhoneNumber()) || validate.validatePhoneOrName(keyword, contact.getName())) {
                contacts.add(contact);
            }
        }
        if (contacts.isEmpty()) {
            System.out.println("Không tìm thấy danh bạ với từ khóa trên !");
        } else {
            System.out.println("Danh bạ cần tìm:");
            contacts.forEach(System.out::println);
        }
    }

    public void displayAll() {
        System.out.printf("| %-10s| %-10s| %-10s| %-10s| %-10s|\n", "Số điện thoại", "Nhóm", "Họ tên", "Giới tính", "Địa chỉ");
        for (Contact contact : contactList) {
            contact.display();
            System.out.println("-----------------------------------------------------------------------");
        }
    }

    public String enterPhoneNumber() {
        int count = 0;
        String phoneNumber;
        while (true) {
            System.out.print("▹ Nhập số điện thoại: ");
            String phone = scanner.nextLine();
            if (!validate.validatePhone(phone)) {
                System.err.println("Số điện thoại không hợp lệ !!!");
                System.out.println(">[Chú ý]: Số điện thoại phải có 10 số (0 - 9) định dạng: (+84)-123456789");
                System.out.println("--------------------");
                count++;
                if(count >= 4) {
                    System.err.println("Bạn nhập sai quá nhiều !! Hệ thống tự động quay lại");
                    new ContactMenu().run();
                }
            } else {
                phoneNumber = phone;
                break;
            }
        }
        return phoneNumber;
    }

    private String enterEmail() {
        int count = 0;
        String email;
        while (true) {
            System.out.print("▹ Nhập email: ");
            String inputEmail = scanner.nextLine();
            if (!validate.validateEmail(inputEmail)) {
                System.out.println("Email không hợp lệ !!!");
                System.out.println(">[Chú ý]: Email phải có dạng: abc@yahoo.com/xyzmanager@gmail.vn/...");
                System.out.println("--------------------");
                count++;
                if(count >= 4) {
                    System.err.println("Bạn nhập sai quá nhiều !! Hệ thống tự động quay lại");
                    new ContactMenu().run();
                }
            } else {
                email = inputEmail;
                break;
            }
        }
        return email;
    }

    public void writeFile(ArrayList<Contact> contactList, String path) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            for (Contact contact : contactList) {
                bufferedWriter.write(contact.getPhoneNumber() + "," + contact.getGroup() + "," + contact.getName() + "," + contact.getGender() + ","
                        + contact.getAddress() + "," + contact.getDateOfBirth() + "," + contact.getEmail() + "\n");
            }
            bufferedWriter.close();
            System.out.println("Write file successfully !");
            System.out.println("--------------------");
        } catch (IOException e) {
            System.out.println("Lỗi ghi file" + e.getMessage());
        }
    }

    public ArrayList<Contact> readFile(String path) {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(",");
                contacts.add(new Contact(strings[0], strings[1], strings[2], strings[3], strings[4], LocalDate.parse(strings[5], DateTimeFormatter.ISO_LOCAL_DATE), strings[6]));
            }
        } catch (IOException e) {
            System.err.println("Lỗi đọc file "+ e.getMessage());
        }
        return contacts;
    }

}