package co.edu.escuelaing.edu.arep.secure.controller;


import co.edu.escuelaing.edu.arep.secure.model.Property;
import co.edu.escuelaing.edu.arep.secure.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyControllerTest {

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    private Property sampleProperty;

    @BeforeEach
    void setUp() {
        sampleProperty = new Property();
        sampleProperty.setAddress("Bogotá");
        sampleProperty.setId(1L);
        sampleProperty.setPrice(100000.0);
        sampleProperty.setSize(50.0);
    }

    @Test
    void testGetAllProperties() {
        when(propertyService.getPropertiesWithFilters(0, 5, null, null, null, null, null))
                .thenReturn(Collections.singletonList(sampleProperty));

        List<Property> properties = propertyController.getAllProperties(0, 5, null, null, null, null, null);

        assertFalse(properties.isEmpty());
        assertEquals(1, properties.size());
        verify(propertyService, times(1)).getPropertiesWithFilters(0, 5, null, null, null, null, null);
    }

    @Test
    void testGetPropertyById() {
        when(propertyService.getPropertyById(1L)).thenReturn(sampleProperty);

        Property property = propertyController.getPropertyById(1L);

        assertNotNull(property);
        assertEquals(1L, property.getId());
        verify(propertyService, times(1)).getPropertyById(1L);
    }

    @Test
    void testCreateProperty() {
        when(propertyService.save(any(Property.class))).thenReturn(sampleProperty);

        ResponseEntity<Property> response = propertyController.createProperty(sampleProperty);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        verify(propertyService, times(1)).save(any(Property.class));
    }

    @Test
    void testUpdateProperty() {
        when(propertyService.updateProperty(eq(1L), any(Property.class))).thenReturn(sampleProperty);

        ResponseEntity<Property> response = propertyController.updateProperty(1L, sampleProperty);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(propertyService, times(1)).updateProperty(eq(1L), any(Property.class));
    }

    @Test
    void testDeleteProperty() {
        doNothing().when(propertyService).delete(1L);

        ResponseEntity<Void> response = propertyController.deleteProperty(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(propertyService, times(1)).delete(1L);
    }
}
