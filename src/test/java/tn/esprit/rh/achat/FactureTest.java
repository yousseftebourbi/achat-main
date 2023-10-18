package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.*;
import tn.esprit.rh.achat.repositories.*;
import tn.esprit.rh.achat.services.FactureServiceImpl;
import tn.esprit.rh.achat.services.ReglementServiceImpl;
import java.util.Optional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {FactureServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class FactureTest {

    @MockBean
    private FactureRepository factureRepository;
    @MockBean
    private OperateurRepository operateurRepository;
    @MockBean
    private DetailFactureRepository detailFactureRepository;
    @MockBean
    private FournisseurRepository fournisseurRepository;
    @MockBean
    private ProduitRepository produitRepository;
    @MockBean
    private ReglementServiceImpl reglementService;

    @Autowired
    private FactureServiceImpl factureService;

    @Test
    void testRetrieveAllFactures() {
        ArrayList<Facture> factures = new ArrayList<>();
        when(factureRepository.findAll()).thenReturn(factures);
        List<Facture> allFactures = factureService.retrieveAllFactures();
        assertSame(factures, allFactures);
        assertTrue(allFactures.isEmpty());
        verify(factureRepository).findAll();
    }

    @Test
    void testAddFacture() {
        Facture facture = new Facture();
        when(factureRepository.save(any(Facture.class))).thenReturn(facture);
        Facture result = factureService.addFacture(facture);
        assertEquals(facture, result);
        verify(factureRepository).save(facture);
    }

    @Test
    void testRetrieveFacture() throws Exception {
        Long factureId = 1L;
        Facture facture = new Facture();
        when(factureRepository.findById(factureId)).thenReturn(Optional.of(facture));

        Facture result = factureService.retrieveFacture(factureId);

        assertEquals(facture, result);

        verify(factureRepository).findById(factureId);
    }

    @Test
    void testCancelFacture() throws Exception {
        Long factureId = 1L;
        Facture facture = new Facture();
        when(factureRepository.findById(factureId)).thenReturn(Optional.of(facture));

        factureService.cancelFacture(factureId);

        verify(factureRepository).findById(factureId);

        assertTrue(facture.getArchivee());
    }

}