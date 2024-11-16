package com.urbantransport.user_service.service;

import com.urbantransport.user_service.model.Admin;
import java.util.List;

public interface AdminService {
  List<Admin> getAll();

  Admin updateAdmin(String id, Admin newadmin);

  void deleteAdmin(String id);

  Admin getAdmin(String id);

  Admin getAdminByEmail(String email);
}
