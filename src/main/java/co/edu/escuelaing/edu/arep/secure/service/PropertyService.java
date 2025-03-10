package co.edu.escuelaing.edu.arep.secure.service;

import co.edu.escuelaing.edu.arep.secure.model.Property;
import co.edu.escuelaing.edu.arep.secure.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getPropertiesWithFilters(int page, int size, String location, Double minPrice, Double maxPrice, Double minSize, Double maxSize) {
        Pageable pageable = PageRequest.of(page, size);
        return propertyRepository.findAllWithFilters(location, minPrice, maxPrice, minSize, maxSize, pageable);
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).orElseThrow(() -> new RuntimeException("Property not found"));
    }

    public Property save(Property property) {
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, Property propertyDetails) {
        Property property = getPropertyById(id);
        property.setAddress(propertyDetails.getAddress());
        property.setPrice(propertyDetails.getPrice());
        property.setSize(propertyDetails.getSize());
        property.setDescription(propertyDetails.getDescription());
        return propertyRepository.save(property);
    }

    public void delete(Long id) {
        propertyRepository.deleteById(id);
    }
}

