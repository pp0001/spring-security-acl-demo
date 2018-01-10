package com.process.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.process.demo.model.LocalEntity;

@Service
@Transactional
public class LocalPermissionService {

    @Autowired
    private MutableAclService aclService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public void addPermissionForUser(LocalEntity targetObj, Permission permission, String username) {
        final Sid sid = new PrincipalSid(username);
        addPermissionForSid(targetObj, permission, sid);
    }

    public void addPermissionForAuthority(LocalEntity targetObj, Permission permission, String authority) {
        final Sid sid = new GrantedAuthoritySid(authority);
        addPermissionForSid(targetObj, permission, sid);
    }
    
    private void addPermissionForSid(LocalEntity targetObj, Permission permission, Sid sid) {
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);
        List<Sid> listSid = java.util.Arrays.asList(sid);
        tt.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                final ObjectIdentity oi = new ObjectIdentityImpl(targetObj.getClass(), targetObj.getId());
                MutableAcl acl = null;
                try {
                    acl = (MutableAcl) aclService.readAclById(oi, listSid);
                } catch (final NotFoundException nfe) {
                    acl = aclService.createAcl(oi);
                    acl.insertAce(acl.getEntries().size(), permission, sid, true);
                    
                  //add entry for other role
                    if(permission == CustomPermission.OWNER_CRU) {
                        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, new GrantedAuthoritySid("ROLE_ADMIN"), true);
                        acl.insertAce(acl.getEntries().size(), BasePermission.READ, new GrantedAuthoritySid("ROLE_USER"), true);
                    }else if(permission == BasePermission.ADMINISTRATION) {
                        acl.insertAce(acl.getEntries().size(), CustomPermission.OWNER_CRU, new GrantedAuthoritySid("ROLE_PO"), true);
                        acl.insertAce(acl.getEntries().size(), BasePermission.READ, new GrantedAuthoritySid("ROLE_USER"), true);
                    }
                    // change object identity owner
                    acl.setOwner(acl.getEntries().get(0).getSid()); 
                    
                    // delete entry in acl_sid where sid != {role}
//                    String sql = "DELETE FROM acl_sid WHERE acl_sid.principal = 1";
                }
                aclService.updateAcl(acl);
            }
        });
    }
    

}
