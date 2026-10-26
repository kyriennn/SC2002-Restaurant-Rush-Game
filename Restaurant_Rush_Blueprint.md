# Stage 1 Gameplay and Test Guide

## Restaurant Rush: Victory and Defeat Guide

This guide is based on the two sample game logs provided. The successful log uses **Game seed 42** and ends in victory after 18 turns. The defeat log also uses 18 turns, but the Waiter waits every turn and no dishes are served.

## What the two runs show

| Run | Paid customers | Unhappy departures | Revenue | Result |
|---|---:|---:|---:|---|
| Successful sample | 5 | 0 | $63.00 | Victory |
| Waited every turn | 0 | 2 | $0.00 | Defeat |

The successful run also served 5 dishes and ended with average paid-customer satisfaction of 74.40/100. It had a Burger order that had just become READY at the end, so the sixth customer did not need to be served for this particular run to show Victory.

## Main strategy

Each turn gives you separate choices for the Waiter and Chef:

1. **Seat customers promptly.** A customer left waiting loses satisfaction, and customers can leave unhappy if ignored.
2. **Take an order as soon as a seated customer is waiting to order.**
3. **Use the Chef to prepare the current order.** Salad and Burger take one preparation unit; Pasta takes two.
4. **Serve ready food on a later turn.** In the sample run, a dish could not be served on the same turn it became ready.
5. **Keep tables moving.** Customers automatically finish eating after a full turn, then pay automatically at the end of a later turn. Seat the next waiting customer when a table becomes available.
6. **Do not choose Wait when a useful task is available.** The defeat example shows that waiting repeatedly allows the queue to grow and customers to leave without spending.

The Chef can prepare an order during the same turn that the Waiter takes it. This is useful because it advances the meal without spending an extra turn.

## Reproducible successful run: seed 42

Follow this sequence from the successful sample log. Choose the matching task by its description in the game; menu option numbers can change as the available tasks change.

| Turn | Waiter action | Chef action / end-of-turn event |
|---:|---|---|
| 1 | Seat Customer 1 at Table 1 | No order to prepare yet. |
| 2 | Take Customer 1's order: Pasta | Cook Pasta for one unit (1/2). |
| 3 | Wait (no useful Waiter task is available in the sample) | Cook Pasta for the second unit; it becomes ready. |
| 4 | Serve Customer 1's Pasta | Customer 1 begins eating. |
| 5 | Seat Customer 2 at Table 2 | Customer 1 finishes eating and becomes ready to pay. |
| 6 | Take Customer 2's order: Salad | Cook Salad; it becomes ready. Customer 1 pays $18. |
| 7 | Serve Customer 2's Salad | Customer 2 begins eating. |
| 8 | Seat Customer 3 at Table 1 | Customer 2 finishes eating and becomes ready to pay. |
| 9 | Take Customer 3's order: Salad | Cook Salad; it becomes ready. Customer 2 pays $9. |
| 10 | Serve Customer 3's Salad | Customer 3 begins eating. |
| 11 | Seat Customer 4 at Table 2 | Customer 3 finishes eating and becomes ready to pay. |
| 12 | Take Customer 4's order: Pasta | Cook Pasta for one unit (1/2). Customer 3 pays $9. |
| 13 | Seat Customer 5 at Table 1 | Cook Pasta for the second unit; it becomes ready. |
| 14 | Serve Customer 4's Pasta | Customer 4 begins eating. |
| 15 | Take Customer 5's order: Salad | Cook Salad; it becomes ready. Customer 4 finishes eating. |
| 16 | Serve Customer 5's Salad | Customer 4 pays $18. |
| 17 | Seat Customer 6 at Table 2 | Customer 5 finishes eating and becomes ready to pay. |
| 18 | Take Customer 6's order: Burger | Cook Burger; it becomes ready. Customer 5 pays $9. |

### End-of-run result from the sample

- Served: **5**
- Paid: **5**
- Unhappy departures: **0**
- Revenue: **$63.00**
- Average satisfaction of paid customers: **74.40/100**
- Game result: **Victory**

## How the defeat happened

In the defeat log, the Waiter chose **Wait** for all 18 turns. No customers were seated, no orders were taken, and the Chef had nothing to prepare. Customers accumulated in the waiting queue and two left unhappy. The run ended with:

- Served: **0**
- Paid: **0**
- Unhappy departures: **2**
- Revenue: **$0.00**
- Game result: **Defeat**

---

# Stage 2 Gameplay and Test Guide

## Restaurant Rush: Victory and Defeat Guide (VIP and Critic customers)

This guide is based on the two Stage 2 sample game logs. Both use **Game seed 42** and 18 turns. The successful log ends in victory with 4 paid customers. The defeat log has the Waiter wait every turn and no dishes are served.

## What is new in Stage 2

Customers now come in three types:

| Type | Satisfaction lost per turn (waiting or seated before being served) | Observed price |
|---|---:|---|
| Regular | 8 | Salad $9.00, Pasta $18.00 |
| VIP | 5 | Salad $8.10 (a 10% lower price than a Regular's Salad) |
| Critic | 12 | Pasta $18.00 |

- A customer's satisfaction stops dropping once their dish is served. The paid-customer average uses the satisfaction at that point.
- A customer leaves unhappy when satisfaction reaches 0 while waiting or seated before service. In the defeat run, Regular Customer 1 and Critic Customer 4 leave.
- The Critic loses satisfaction fastest, so seat and serve them promptly. The VIP is the most patient but pays less for the same dish.

## What the two runs show

| Run | Served | Paid customers | Unhappy departures | Revenue | Result |
|---|---:|---:|---:|---:|---|
| Successful sample | 5 | 4 | 0 | $53.10 | Victory |
| Waited every turn | 0 | 0 | 2 | $0.00 | Defeat |

The successful run ended with average paid-customer satisfaction of 72.25/100. At the end, Customer 5 was ready to pay and Customer 6 had just been seated, so neither needed to finish for this run to show Victory.

## Arrival schedule (seed 42, Stage 2)

Arrivals are identical in both runs. A customer appears in the queue at the start of the listed arrival turn.

| Customer | Type | First appears in queue |
|---|---|---|
| 1 | Regular | Turn 1 |
| 2 | VIP | Turn 4 |
| 3 | Regular | Turn 7 |
| 4 | Critic | Turn 10 |
| 5 | Regular | Turn 13 |
| 6 | Regular | Turn 16 |

## Main strategy

1. **Serve ready food before seating the next customer.** In every turn where both were possible (turns 4, 7, 10, 14, 17), the successful run served the dish and seated the queued customer on the following turn.
2. **Seat a waiting customer as soon as a table is free and nothing is ready to serve.**
3. **Take an order as soon as a seated customer has none.** The Chef can start cooking in the same turn.
4. **Use the Chef every turn there is an order.** Salad takes one unit, Pasta takes two.
5. **A dish cannot be served on the turn it becomes ready.** Serve it on the next turn.
6. **Customers finish eating one turn after being served, then pay at the end of the following turn.** Tables free up automatically.
7. **Never Wait when another task is available.** The defeat run shows what happens.

## Reproducible successful run: seed 42, Stage 2

Choose the matching task by its description in the game. Menu option numbers change as the available tasks change.

| Turn | Waiter action | Chef action / end-of-turn event |
|---:|---|---|
| 1 | Seat Customer 1 (Regular) at Table 1 | Nothing to prepare. |
| 2 | Take Customer 1's order: Pasta ($18.00) | Cook Pasta for one unit (1/2). |
| 3 | Wait (the only task available) | Cook Pasta for the second unit; it becomes ready. |
| 4 | Serve Pasta to Customer 1 (chosen over seating Customer 2) | Customer 2 (VIP) arrives. Nothing to prepare. |
| 5 | Seat Customer 2 (VIP) at Table 2 | Nothing to prepare. Customer 1 finishes eating and is ready to pay. |
| 6 | Take Customer 2's order: Salad ($8.10) | Cook Salad; it becomes ready. Customer 1 pays $18.00. |
| 7 | Serve Salad to Customer 2 (chosen over seating Customer 3) | Customer 3 (Regular) arrives. Nothing to prepare. |
| 8 | Seat Customer 3 at Table 1 | Nothing to prepare. Customer 2 finishes eating and is ready to pay. |
| 9 | Take Customer 3's order: Salad ($9.00) | Cook Salad; it becomes ready. Customer 2 pays $8.10. |
| 10 | Serve Salad to Customer 3 (chosen over seating Customer 4) | Customer 4 (Critic) arrives. Nothing to prepare. |
| 11 | Seat Customer 4 (Critic) at Table 2 | Nothing to prepare. Customer 3 finishes eating and is ready to pay. |
| 12 | Take Customer 4's order: Pasta ($18.00) | Cook Pasta for one unit (1/2). Customer 3 pays $9.00. |
| 13 | Seat Customer 5 at Table 1 | Customer 5 (Regular) arrives. Cook Pasta for the second unit; it becomes ready. |
| 14 | Serve Pasta to Customer 4 (chosen over taking Customer 5's order) | Nothing to prepare. |
| 15 | Take Customer 5's order: Pasta ($18.00) | Cook Pasta for one unit (1/2). Customer 4 finishes eating and is ready to pay. |
| 16 | Wait (the only task available) | Customer 6 (Regular) arrives. Cook Pasta for the second unit; it becomes ready. Customer 4 pays $18.00. |
| 17 | Serve Pasta to Customer 5 (chosen over seating Customer 6) | Nothing to prepare. |
| 18 | Seat Customer 6 at Table 2 | Nothing to prepare. Customer 5 finishes eating and is ready to pay. |

### End-of-run result from the sample

- Served: **5**
- Paid: **4**
- Unhappy departures: **0**
- Revenue: **$53.10** ($18.00 + $8.10 + $9.00 + $18.00)
- Average satisfaction of paid customers: **72.25/100** (satisfaction at serving: 76, 85, 76, 52)
- Game result: **Victory**

## How the defeat happened

In the defeat log, the Waiter chose **Wait** on all 18 turns. Both tables stayed empty, no orders were taken, and the Chef never had anything to prepare.

| Turn | Waiter action | Event |
|---:|---|---|
| 1 | Wait | Customer 1 waiting (satisfaction 100). |
| 2 | Wait | |
| 3 | Wait | |
| 4 | Wait | Customer 2 (VIP) arrives. |
| 5 | Wait | |
| 6 | Wait | |
| 7 | Wait | Customer 3 (Regular) arrives. |
| 8 | Wait | |
| 9 | Wait | |
| 10 | Wait | Customer 4 (Critic) arrives. |
| 11 | Wait | |
| 12 | Wait | |
| 13 | Wait | Customer 5 (Regular) arrives. Customer 1 ran out of patience and left. |
| 14 | Wait | |
| 15 | Wait | |
| 16 | Wait | Customer 6 (Regular) arrives. |
| 17 | Wait | |
| 18 | Wait | Customer 4 (Critic) ran out of patience and left. |

The run ended with:

- Served: **0**
- Paid: **0**
- Unhappy departures: **2**
- Revenue: **$0.00**
- Game result: **Defeat**

---

# Stage 3 Gameplay and Test Guide

## Restaurant Rush: Victory and Defeat Guide (Host, combo meals and Happy Hour)

This guide is based on two reproducible Stage 3 runs of the `control/entities-stage3` branch at commit `0a1a2d0`. Both use **Game seed 42** and run all 18 turns. The successful run activates Happy Hour on turn 4 and ends in victory; the defeat run declines Happy Hour and has the Waiter wait every turn.

## What is new in Stage 3

The Host automatically seats the earliest waiting customer at the first free table, at most once per turn. The Waiter still has a separate action and may seat another customer. There is no Host choice prompt in this version.

The menu adds three combos made from two existing items:

| Combo | Price | Preparation units |
|---|---:|---:|
| Salad + Burger | $21.60 | 2 |
| Salad + Pasta | $24.30 | 3 |
| Burger + Pasta | $29.70 | 3 |

Each combo costs 90% of its components' combined price, rounded to the nearest cent, and needs the sum of their preparation units. Regular and VIP customers choose from all six menu entries using the seed; the Critic chooses the highest-priced entry. The manager never chooses their food.

Happy Hour can be activated once, at the start of a turn. It lasts for that turn and the next. At the start of each active turn, customers still waiting for a table or food recover 15 satisfaction, up to 100. Orders taken while Happy Hour is active get 20% off. For a VIP, apply the 10% VIP discount first, then the Happy Hour discount. The final price is rounded to the nearest cent and locked when the order is taken.

## What the two runs show

| Run | Served | Paid customers | Unhappy departures | Revenue | Result |
|---|---:|---:|---:|---:|---|
| Successful sample, Happy Hour on turn 4 | 6 | 5 | 0 | $90.25 | Victory |
| Waited every turn, no Happy Hour | 0 | 0 | 2 | $0.00 | Defeat |

The successful run ended with average paid-customer satisfaction of **82.20/100**. Customer 6 was served on turn 18 but could not finish eating and pay within the game. The defeat run had no paid customers, so the average displays **N/A**.

## Arrival schedule (seed 42, Stage 3)

One customer joins the queue at the start of each listed turn. The Host may seat that customer immediately if a table is free.

| Customer | Type | Arrival turn |
|---|---|---:|
| 1 | Regular | 1 |
| 2 | VIP | 4 |
| 3 | Regular | 7 |
| 4 | Critic | 10 |
| 5 | Regular | 13 |
| 6 | Regular | 16 |

## Main strategy

1. **Activate Happy Hour before turn 4.** Answer N before turns 1–3 and Y before turn 4. It displays 2 remaining turns on turn 4, 1 on turn 5 and 0 on turn 6.
2. **Let the Host seat customers automatically.** The Host acts before the Waiter, so the Waiter can take a newly seated customer's order in the same turn.
3. **Give the Waiter the most useful available task.** Serve a ready dish first; otherwise take a seated customer's order. The Waiter can also seat someone if the Host could not.
4. **Use the Chef whenever an order needs preparation.** A combo needs the sum of its two items' units. An order taken by the Waiter can receive its first preparation unit that same turn.
5. **Serve and collect payment on later turns.** A dish finished by the Chef this turn becomes READY and cannot be served until a later turn. The customer finishes eating on a later turn and pays no earlier than the following turn.

Choose each task by its description rather than its menu number; the numbers change with the available tasks.

## Reproducible successful run: seed 42, Stage 3

Answer **N** to Happy Hour before turns 1, 2 and 3, then **Y** before turn 4. After activation, the game does not ask again. The Host actions below are automatic. “Nothing to prepare” means the Chef gives no choice prompt.

| Turn | Waiter action | Host, Chef action / end-of-turn event |
|---:|---|---|
| 1 | Take Customer 1's order: Pasta ($18.00) | Host seats Customer 1 at Table 1. Cook Pasta for one unit (1/2). |
| 2 | Wait | Cook Pasta for the second unit; it becomes READY. |
| 3 | Serve Customer 1's Pasta | Nothing to prepare. Customer 1 begins eating. |
| 4 | Take VIP Customer 2's order: Salad + Burger Combo ($15.55) | Happy Hour starts. Host seats Customer 2 at Table 2. Cook combo unit 1/2. Customer 1 finishes eating. |
| 5 | Wait | Cook combo unit 2/2; it becomes READY. Customer 1 pays $18.00. |
| 6 | Serve Customer 2's combo | Nothing to prepare. Happy Hour has ended. |
| 7 | Take Customer 3's order: Salad ($9.00) | Host seats Customer 3 at Table 1. Cook Salad; it becomes READY. Customer 2 finishes eating. |
| 8 | Serve Customer 3's Salad | Nothing to prepare. VIP Customer 2 pays the locked $15.55. |
| 9 | Wait | Nothing to prepare. Customer 3 finishes eating and becomes ready to pay. |
| 10 | Take Critic Customer 4's order: Burger + Pasta Combo ($29.70) | Host seats Customer 4 at Table 2. Cook combo unit 1/3. Customer 3 pays $9.00. |
| 11 | Wait | Cook combo unit 2/3. |
| 12 | Wait | Cook combo unit 3/3; it becomes READY. |
| 13 | Serve Customer 4's combo | Host seats Customer 5 at Table 1. Nothing to prepare. |
| 14 | Take Customer 5's order: Pasta ($18.00) | Cook Pasta for one unit (1/2). Customer 4 finishes eating. |
| 15 | Wait | Cook Pasta for the second unit; it becomes READY. Customer 4 pays $29.70. |
| 16 | Serve Customer 5's Pasta | Host seats Customer 6 at Table 2. Nothing to prepare. |
| 17 | Take Customer 6's order: Salad ($9.00) | Cook Salad; it becomes READY. Customer 5 finishes eating. |
| 18 | Serve Customer 6's Salad | Nothing to prepare. Customer 5 pays $18.00. Customer 6 cannot pay before the game ends. |

The VIP combo demonstrates both discounts: $21.60 × 0.90 = $19.44, then $19.44 × 0.80 = $15.552, rounded to **$15.55**. It was ordered on turn 4 and paid on turn 8 for the same locked amount. The VIP recovers satisfaction at the start of turn 5, capped at 100. The Critic's Burger + Pasta combo takes three Chef units on turns 10–12, becomes READY on turn 12, and is served on turn 13.

### End-of-run result from the sample

- Served: **6**
- Paid: **5**
- Unhappy departures: **0**
- Revenue: **$90.25** ($18.00 + $15.55 + $9.00 + $29.70 + $18.00)
- Average satisfaction of paid customers: **82.20/100**
- Turns used: **18**
- Game result: **Victory**

## How the defeat happened

For all 18 turns, answer **N** to Happy Hour and choose **Wait** for the Waiter. The Chef has no orders and waits automatically. The Host still seats customers when a table is free, but no order is taken or served.

| Turn | Waiter action | Event |
|---:|---|---|
| 1 | Wait | Customer 1 (Regular) arrives; Host seats them at Table 1. |
| 2 | Wait | |
| 3 | Wait | |
| 4 | Wait | Customer 2 (VIP) arrives; Host seats them at Table 2. |
| 5 | Wait | |
| 6 | Wait | |
| 7 | Wait | Customer 3 (Regular) arrives and waits for a table. |
| 8 | Wait | |
| 9 | Wait | |
| 10 | Wait | Customer 4 (Critic) arrives and waits for a table. |
| 11 | Wait | |
| 12 | Wait | |
| 13 | Wait | Customer 5 (Regular) arrives. Customer 1 loses all satisfaction and leaves; Table 1 becomes free. |
| 14 | Wait | Host seats Customer 3 at Table 1. |
| 15 | Wait | |
| 16 | Wait | Customer 6 (Regular) arrives. |
| 17 | Wait | |
| 18 | Wait | Customer 4 loses all satisfaction and leaves. |

The run ended with:

- Served: **0**
- Paid: **0**
- Unhappy departures: **2**
- Revenue: **$0.00**
- Average satisfaction of paid customers: **N/A**
- Turns used: **18**
- Game result: **Defeat**

