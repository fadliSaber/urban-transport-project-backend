package com.urbantransport.user_service.service.implementation;

import com.urbantransport.user_service.auth.AuthenticationResponse;
import com.urbantransport.user_service.config.JwtService;
import com.urbantransport.user_service.exception.NotFoundException;
import com.urbantransport.user_service.model.Admin;
import com.urbantransport.user_service.repository.AdminRepository;
import com.urbantransport.user_service.service.AdminService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final AdminRepository adminRepository;
  private final JwtService jwtService;

  @Override
  public List<Admin> getAll() {
    return adminRepository.findAll();
  }

  @Override
  public Admin addAdmin(Admin admin) {
    return adminRepository.save(admin);
  }

  @Override
  public Admin updateAdmin(String id, Admin newadmin) {
    Admin oldadmin = adminRepository
      .findById(id)
      .orElseThrow(() ->
        new NotFoundException("Admin with id " + id + " not found")
      );

    oldadmin.setEmail(newadmin.getEmail());
    oldadmin.setFirstName(newadmin.getFirstName());
    oldadmin.setLastName(newadmin.getLastName());
    return adminRepository.save(oldadmin);
  }

  @Override
  public void deleteAdmin(String id) {
    adminRepository.deleteById(id);
  }

  @Override
  public Admin getAdmin(String id) {
    return adminRepository
      .findById(id)
      .orElseThrow(() ->
        new NotFoundException("Admin with id " + id + " not found")
      );
  }

  @Override
  public AuthenticationResponse updateAdminEmail(String id, String email) {
    Admin admin = adminRepository
      .findById(id)
      .orElseThrow(() ->
        new NotFoundException("Admin with id " + id + " not found")
      );
    admin.setEmail(email);
    adminRepository.save(admin);
    var jwtToken = jwtService.generateToken(admin);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  @Override
  public Admin getAdminByEmail(String email) {
    return adminRepository
      .findByEmail(email)
      .orElseThrow(() ->
        new NotFoundException("Admin with email " + email + " not found")
      );
  }
}
