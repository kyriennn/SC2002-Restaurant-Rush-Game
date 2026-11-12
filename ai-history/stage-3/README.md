# Generative AI Declaration (Yeo Wei Xuan)

**Tool used:** DeepSeek-V4.1-Flash

## Prompt

> Without telling me the exact code, guide me on how I should correctly order my discount calculations such that customer-specific discounts and meal discounts are applied consistently.
> What implementation details should I be careful about?

## AI Response

> When applying multiple percentage discounts, they do not simply add up. You must apply them one at a time in a defined order.
> Since you asked for the customer type discount first, you should compute the VIP's 10% discount on the menu item's base price first, then apply the Combo Meal discount to that reduced amount.
> The order matters because percentage discounts are not commutative: a 10% discount followed by another 10% discount gives 81% of the original price, not 80%.
>
> Key implementation considerations include:
>
> 1. Round the result to the nearest cent rather than truncating, preferably using round-half-up rounding.
> 2. Lock the final charged price when the order is placed so that later events cannot change the bill.
> 3. Apply the discount ordering consistently throughout the system and document the chosen approach to ensure deterministic and reproducible results.

## Summary of AI Assistance Received

The AI explained that multiple percentage discounts should be applied sequentially in a defined order rather than added together. 
It highlighted that the order of applying discounts affects the final amount and that a consistent discount sequence should be used throughout the program. 

## How the Assistance Was Used

I used this guidance to review and verify the pricing logic in my project. 
Specifically, it helped me reason about the order in which discounts should be applied and identify potential issues related to rounding and price calculation. 
The AI did not generate any code that was directly copied into the project. All implementation decisions, testing, and code development were carried out by me.

## Purpose of the Assistance
The assistance was used to clarify how stacked percentage discounts should be applied and to identify common implementation pitfalls, such as discount ordering, cent-level rounding, and price locking. 
This helped me validate the correctness and reproducibility of my project's pricing and revenue calculations, so that everything is consistent and the discount gives the correct amount with or without the combo.
