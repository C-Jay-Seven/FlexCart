package com.potragis.FlexCart.service.client;

import com.potragis.FlexCart.dto.client.ClientRequest;
import com.potragis.FlexCart.dto.client.ClientResponse;
import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.entity.Role;
import com.potragis.FlexCart.model.enums.RoleName;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IClientService {

    ClientResponse setIsActive(Long clientId, boolean isActive);
    ClientResponse createClient(ClientRequest clientRequest);
    ClientResponse getClientById(Long clientId);
    List<ClientResponse> getAllClient();
    ClientResponse updateClient(Long id, ClientRequest clientRequest);
    void deleteClient(Long clientId, RoleName roleName);
    String updateAvatar(Long clientId, MultipartFile file);
    ClientResponse getCurrentSeller(Long clientId);

}
