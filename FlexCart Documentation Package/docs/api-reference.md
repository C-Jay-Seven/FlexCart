# API Reference

All endpoints are versioned under `/api/v1`. Request/response bodies use the DTOs in the `dto` package (Request/Response pairs per resource); bean-validation errors are surfaced as HTTP 400 with a field → message map, via `ValidationExceptionHandler`.

← [Back to README](../../README.md)

## Client — `/api/v1/client`

| Method | Path | Description |
|---|---|---|
| POST | `/` | Create a client account |
| GET | `/{clientId}` | Fetch a single client |
| GET | `/` | List all clients |
| PUT | `/{id}` | Update a client |
| DELETE | `/{id}` | Delete a client |

## Products — `/api/v1/products`

| Method | Path | Description |
|---|---|---|
| POST | `/` | Create a product |
| GET | `/{id}` | Fetch a single product |
| GET | `/` | List all products |
| PUT | `/{id}` | Update a product |
| DELETE | `/{id}` | Delete a product |

## Reviews — `/api/v1/products/{productId}/reviews`

| Method | Path | Description |
|---|---|---|
| POST | `/{productId}/reviews` | Add a review to a product |
| GET | `/{productId}/reviews` | List a product's reviews |
| PUT | `/{productId}/review/{clientId}` | Update a client's review for a product |

## Images — `/api/v1/images`

| Method | Path | Description |
|---|---|---|
| POST | `/` | Attach an image to a product |
| GET | `/product/{productId}` | List images for a product |
| DELETE | `/{imageId}` | Delete an image |

## Discounts — `/api/v1/discounts`

| Method | Path | Description |
|---|---|---|
| POST | `/` | Create a discount |
| GET | `/{id}` | Fetch a single discount |
| GET | `/` | List all discounts |
| PUT | `/{id}` | Update a discount |
| DELETE | `/{id}` | Delete a discount |

## Cart — `/api/v1/cart`

| Method | Path | Description |
|---|---|---|
| GET | `/{clientId}` | Fetch a client's cart |
| POST | `/{clientId}/items` | Add a product line item to the cart |
| PUT | `/{clientId}/items/{cartItemId}` | Update a cart line item |
| DELETE | `/{clientId}/items/{cartItemId}` | Remove a cart line item |

## Orders — `/api/v1/orders`

| Method | Path | Description |
|---|---|---|
| GET | `/client/{clientId}` | List a client's orders |
| GET | `/{orderId}` | Fetch a single order |
| POST | `/client/{clientId}` | Create an order for a client |
| DELETE | `/{orderId}` | Cancel an order |

## Payments — `/api/v1/payments`

| Method | Path | Description |
|---|---|---|
| POST | `/` | Record a payment |
| GET | `/{id}` | Fetch a single payment |
| GET | `/order/{orderId}` | List payments for an order |
| PUT | `/{id}` | Update a payment |
| DELETE | `/{id}` | Delete a payment |

## Refunds — `/api/v1/refunds`

| Method | Path | Description |
|---|---|---|
| POST | `/` | Create a refund request |
| GET | `/{id}` | Fetch a single refund |
| GET | `/order/{orderId}` | List refunds for an order |
| PUT | `/{id}` | Update a refund |
| DELETE | `/{id}` | Delete a refund |
