package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private TorpedoStore mockPrimaryTorpedoStore;
  private TorpedoStore mockSecondaryTorpedoStore;
  private GT4500 ship;

  @BeforeEach
  public void init(){
    mockPrimaryTorpedoStore = mock(TorpedoStore.class);
    mockSecondaryTorpedoStore = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimaryTorpedoStore, mockSecondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore,times(1)).fire(1);
    verify(mockSecondaryTorpedoStore,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    assertEquals(true, result2);

    verify(mockPrimaryTorpedoStore,times(1)).fire(1);
    verify(mockSecondaryTorpedoStore,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_Empty_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(mockPrimaryTorpedoStore,times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Secondary_Empty_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(mockPrimaryTorpedoStore,times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Secondary_Empty_Failure(){
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);

    verify(mockPrimaryTorpedoStore,times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore,times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).fire(0);
  }

  @Test
  public void fireTorpedo_Single_Primary_Empty_Failure(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);

    verify(mockPrimaryTorpedoStore,times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore,times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_ALL_Failure(){
    // Arrange

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);

    verify(mockPrimaryTorpedoStore,times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore,times(1)).fire(1);
  }
}
