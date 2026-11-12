# Restaurant Rush

## Stage 2 Customer Behaviour

| Customer | Satisfaction loss per waiting turn | Bill |
|---|---:|---|
| Regular | 8 | Full price |
| VIP | 5 | 10% discount |
| Critic | 12 | Full price |

Satisfaction decreases while customers are waiting or seated
and have not been served.

Critics choose the highest-priced menu item. If prices tie,
they choose the first item in menu order.

Regular and VIP customers choose food using seeded randomness.
The current game uses seed 42.

Customers arrive on turns 1, 4, 7, 10, 13 and 16.
The turn-4 arrival is a VIP, and the turn-10 arrival is a Critic.

All customer types follow the same ordering, serving and payment flow.
Their different behaviours are handled through customer methods.

## Stage 3: Host, combo meals and Happy Hour

The Stage 3 game includes a Host, who automatically seats at most one
queued customer per turn when a table is available. The Waiter can still
seat another customer during the Waiter phase.

The menu also includes every pairwise combo of Salad, Burger and Pasta.
Combos use the sum of their components' preparation units and cost 90% of
the combined price, rounded to the nearest cent. Regular and VIP customers
choose randomly from the complete menu using the configured seed. Critics
choose the highest-priced menu item, including combos; ties use menu order.

At the start of any turn, the manager may activate Happy Hour once per game.
It lasts for that turn and the next turn. Orders taken during Happy Hour
receive 20% off after any customer-specific discount, and the final price is
locked when the order is placed. Customers still waiting for a table or
food recover 15 satisfaction at the start of each active turn. The current
remaining duration is displayed at the start of every turn.

## Gameplay and Test Runs

See the [Stages Gameplay and Test Guide](Restaurant_Rush_Blueprint.md).