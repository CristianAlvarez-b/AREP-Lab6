package co.edu.escuelaing.edu.arep.secure.service;


import co.edu.escuelaing.edu.arep.secure.model.Property;
import co.edu.escuelaing.edu.arep.secure.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private Property sampleProperty;

    @BeforeEach
    void setUp() {
        sampleProperty = new Property();
        sampleProperty.setId(1L);
        sampleProperty.setAddress("Calle 123");
        sampleProperty.setPrice(200000.0);
        sampleProperty.setSize(60.0);
        sampleProperty.setDescription("Amplio apartamento en el centro");
    }

    @Test
    void testGetPropertiesWithFilters() {
        Pageable pageable = PageRequest.of(0, 5);
        when(propertyRepository.findAllWithFilters(null, null, null, null, null, pageable))
                .thenReturn(Collections.singletonList(sampleProperty));

        List<Property> properties = propertyService.getPropertiesWithFilters(0, 5, null, null, null, null, null);

        assertFalse(properties.isEmpty());
        assertEquals(1, properties.size());
        verify(propertyRepository, times(1)).findAllWithFilters(null, null, null, null, null, pageable);
    }

    @Test
    void testGetPropertyById_Found() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(sampleProperty));

        Property property = propertyService.getPropertyById(1L);

        assertNotNull(property);
        assertEquals(1L, property.getId());
        verify(propertyRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPropertyById_NotFound() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> propertyService.getPropertyById(1L));
        assertEquals("Property not found", exception.getMessage());
    }

    @Test
    void testSaveProperty() {
        when(propertyRepository.save(any(Property.class))).thenReturn(sampleProperty);

        Property savedProperty = propertyService.save(sampleProperty);

        assertNotNull(savedProperty);
        assertEquals(1L, savedProperty.getId());
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testUpdateProperty() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(sampleProperty));
        when(propertyRepository.save(any(Property.class))).thenReturn(sampleProperty);

        Property updatedProperty = propertyService.updateProperty(1L, sampleProperty);

        assertNotNull(updatedProperty);
        assertEquals(1L, updatedProperty.getId());
        verify(propertyRepository, times(1)).findById(1L);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testDeleteProperty() {
        doNothing().when(propertyRepository).deleteById(1L);

        propertyService.delete(1L);

        verify(propertyRepository, times(1)).deleteById(1L);
    }
}
