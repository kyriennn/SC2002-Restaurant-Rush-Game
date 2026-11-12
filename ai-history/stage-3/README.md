# Generative AI Declaration (CHUA KAI WEN)

**Tool used:** GPT-6 Sol High

## Prompt

> I am planning Stage 3 of my Restaurant Rush project. It adds a Host who seats at most one queued customer per turn, ComboMeals made from two existing menu items, and a two-turn Happy Hour pricing policy. Please suggest suitable design ideas for how these features could fit into the existing game. Identify decisions I should make about combo selection, turn timing, and when an order’s price is fixed. Do not write code, pseudocode, or a complete solution; I will design and implement the features myself.

## AI Response

> A useful way to plan Stage 3 is to treat each addition as part of the existing turn:

> Host: Give the Host one seating opportunity per turn, separate from the Waiter’s task. Decide whether the Host acts before the Waiter; that would let the Waiter take a newly seated customer’s order in the same turn. Document which queued customer and free table the Host chooses.
> ComboMeals: Add combos to the menu using two existing items. Their preparation units are the sum of both items’ units, and their menu price is 90% of the combined price, rounded to cents. Document a customer selection rule, including how Stage 2 customer types choose when combos are available.
> Happy Hour: Decide precisely where “start of turn” falls in your turn sequence. Activation is available once, covers that turn and the next, and should show its remaining duration. During each active turn, eligible waiting customers recover 15 satisfaction, capped at 100.
> Order price: Calculate and store the final bill when the order is taken. Apply any VIP discount first, then the 20% Happy Hour discount if active. A later change in Happy Hour must not change ? an existing order’s price.

> For your own checks, consider a turn where both Host and Waiter can seat customers, a VIP ordering a combo during Happy Hour, and an order paid for after Happy Hour ends.

## Summary of AI Assistance Received

The AI suggested ideas for integrating the Host, ComboMeals, and Happy Hour into Stage 3. It identified decisions to make about seating order, customer combo selection, turn timing, and fixing an order’s price when it is placed. It also suggested situations I could check during development.

## How the Assistance Was Used

I used the response as a planning checklist while considering the Stage 3 features. I made the design and implementation decisions myself. The AI did not provide code or a complete solution.

## Purpose of the Assistance

I used GAI to generate key ideas and identify decisions I needed to make before implementing Stage 3, particularly how the new features interact with the existing turn sequence and order pricing.
