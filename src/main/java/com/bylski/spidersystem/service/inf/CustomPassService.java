package com.bylski.spidersystem.service.inf;

import com.bylski.spidersystem.model.CustomPass;
import com.bylski.spidersystem.model.dto.CustomPassDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPassService {
    void createCustomPass(CustomPassDTO customPassDTO);

    CustomPass getCustomPassByCardNumber(String cardNumber);

    Page<CustomPass> getCustomPassByFirstName(String name, Pageable pageable);
    Page<CustomPass> getCustomPassByLastName(String name, Pageable pageable);

    void updateCustomPass(Long passId, CustomPassDTO customPassDTO);
    void deleteCustomPass(Long passId);

    void takePunch(Long passId);
    void givePunch(Long passId);
}
