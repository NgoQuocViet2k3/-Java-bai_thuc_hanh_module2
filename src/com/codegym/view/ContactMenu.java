package com.codegym.view;

import com.codegym.controller.ContactManagement;
import com.codegym.model.Contact;

import java.util.ArrayList;
import java.util.Scanner;

public class ContactMenu {
    public Scanner scanner = new Scanner(System.in);
    ContactManagement contactManagement = new ContactManagement();


    public void run() {

        int choice = -1;
        do {
            menuContact();
            System.out.println("");
            System.out.println("Nhập lựa chọn của bạn");
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    contactManagement.displayAll();
                    break;
                }
                case 2: {
                    contactManagement.addContact();
                    break;
                }
                case 3:
                    System.out.println("▹ Nhập số điện thoại cần sửa VD: (+84)-09020304054:");
                    String phoneEdit = scanner.nextLine();
                    if (phoneEdit.equals("")) {
                        run();
                    } else {
                        contactManagement.updateContact(phoneEdit);
                    }
                    break;
                case 4:
                    System.out.println("▹ Nhập số điện thoại cần xóa VD: (+84)-095242436:");
                    String phoneDelete = scanner.nextLine();
                    if (phoneDelete.equals("")) {
                        run();
                    } else {
                        contactManagement.deleteContact(phoneDelete);
                    }
                    break;
                case 5:
                    System.out.println("▹ Nhập từ khóa:");
                    String keyword = scanner.nextLine();
                    contactManagement.searchContactByNameOrPhone(keyword);
                    break;
                case 6:
                    ArrayList<Contact> contactArrayList = contactManagement.readFile(ContactManagement.PATH_NAME_CONTACT);
                    contactArrayList.forEach(Contact::display);
                    System.out.println("Read file successfully !");
                    break;
                case 7:
                    contactManagement.writeFile(contactManagement.getContactList(), ContactManagement.PATH_NAME_CONTACT);
                    break;
                case 8:
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không tồn tại, mời bạn nhập lại !!!");
                    break;
            }
        } while (choice !=8);


    }

    private void menuContact() {
        System.out.println("---- CHƯƠNG TRÌNH QUẢN LÍ DANH BẠ ----");
        System.out.println("Chọn chức năng theo số (để tiếp tục");
        System.out.println("1.Xem danh sách");
        System.out.println("2.Thêm mới");
        System.out.println("3.Cập nhật");
        System.out.println("4.Xóa");
        System.out.println("5.Tìm kiếm");
        System.out.println("6.Đọc từ file");
        System.out.println("7.Ghi vào file");
        System.out.println("8.Thoát");

    }

}
