package com.urbantransport.user_service.service;

import com.urbantransport.user_service.auth.AuthenticationResponse;
import com.urbantransport.user_service.model.Admin;
import java.util.List;

public interface AdminService {
  List<Admin> getAll();

  Admin addAdmin(Admin admin);

  Admin updateAdmin(String id, Admin newadmin);

  void deleteAdmin(String id);

  Admin getAdmin(String id);

  AuthenticationResponse updateAdminEmail(String id, String email);

  Admin getAdminByEmail(String email);
}
