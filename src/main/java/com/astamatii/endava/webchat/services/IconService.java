package com.astamatii.endava.webchat.services;

import com.astamatii.endava.webchat.models.Icon;
import com.astamatii.endava.webchat.repositories.IconRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IconService {
    private final ResourceLoader resourceLoader;
    private final IconRepository iconRepository;

    public void saveIcon(Icon icon){
        iconRepository.save(icon);
    }

    public void saveIconByFilePath(String filePath) {
        Icon icon = new Icon();
        icon.setFilePath(filePath);
        iconRepository.save(icon);
    }

    public String getIconFilePath(Long id) {
        Optional<Icon> optionalIcon = iconRepository.findById(id);
        return optionalIcon.map(Icon::getFilePath).orElse(null);
    }

    public Icon getIconById(long id){
        Optional<Icon> optionalIcon = iconRepository.findById(id);
        return optionalIcon.orElse(null);
    }
}
