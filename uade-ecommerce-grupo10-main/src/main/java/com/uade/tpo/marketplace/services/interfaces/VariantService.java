package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.Variant;
import java.util.List;
import java.util.Optional;

public interface VariantService {

    // Obtener todas las variantes de un producto específico (ej: todos los talles de una zapatilla)
    List<Variant> getVariantsByProductId(Long productId);

    // Obtener una variante específica por su ID
    Optional<Variant> getVariantById(Long id);

    // Crear una nueva variante asociada a un producto
    Variant createVariant(Long productId, int size, int stock);

    // Actualizar los datos de una variante (útil para reposición de stock)
    Optional<Variant> updateVariant(Long id, int size, int stock);

    // Método específico para actualizar solo el stock (muy usado tras una compra)
    boolean updateStock(Long id, int newStock);

    // Eliminar una variante y confirmar éxito
    boolean deleteVariant(Long id);
}
