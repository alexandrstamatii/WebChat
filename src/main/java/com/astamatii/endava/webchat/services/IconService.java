package com.astamatii.endava.webchat.services;

import com.astamatii.endava.webchat.models.Icon;
import com.astamatii.endava.webchat.repositories.IconRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IconService {
    private final ResourceLoader resourceLoader;
    private final IconRepository iconRepository;

    public IconService(ResourceLoader resourceLoader, IconRepository iconRepository) {
        this.resourceLoader = resourceLoader;
        this.iconRepository = iconRepository;
    }

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
