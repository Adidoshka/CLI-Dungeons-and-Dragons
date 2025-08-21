# Dungeons and Dragons CLI Game

## 📖 Overview

This project is a **single-player multi-level dungeon crawler game** implemented in Java. The game is played in the terminal (CLI) and follows a Dungeons & Dragons style where the player must survive and defeat enemies across multiple levels to win.

The program emphasizes **Object-Oriented Programming (OOP)** principles and patterns such as the **Visitor** and **Observer** design patterns.

---

## 🎮 Game Description

* The game board consists of:

  * **Player (@)** – controlled by the user.
  * **Enemies (B, s, k, M, etc.)** – controlled by the computer.
  * **Walls (#)** – block movement.
  * **Empty spaces (.)** – free to walk through.
* The game progresses across multiple levels (loaded from `.txt` files).
* The player must defeat all enemies in a level to move to the next one.
* The game ends if:

  * All levels are completed (victory 🎉).
  * The player dies (game over ☠️).

---

## ⚔️ Game Flow

1. User selects a **player character** from a predefined list.
2. Game begins on **level 1**.
3. Each **Game Tick (round)**:

   * Player performs a single action.
   * All enemies perform a single action.
4. Level ends when all enemies are dead → Next level loads.
5. Game ends when:

   * Player completes all levels ✅
   * Player dies ❌

---

## 🗺️ Game Board Representation

* Implemented as a **2D array of characters**.
* Each tile has its own class:

  * **Empty** – walkable space.
  * **Wall** – blocks movement.
  * **Unit** – player or enemy.

---

## 👤 Player

### General Properties

* Name
* Health (pool & current amount)
* Attack points
* Defense points
* Experience (XP)
* Player Level (starts at 1)

### Leveling System

* Gain XP by killing enemies.
* Level up when XP ≥ `50 × current level`.
* On level up:

  * Increase health, attack, defense.
  * Reset HP to full.
  * XP reduced accordingly.

### Player Classes

1. **Warrior**

   * **Ability**: *Avenger’s Shield* → Hits a random enemy (range < 3) for 10% of max HP and heals based on defense.
   * Has **cooldown system**.

2. **Mage**

   * **Ability**: *Blizzard* → Randomly hits enemies in range using mana.
   * Uses **mana** as a resource.

3. **Rogue**

   * **Ability**: *Fan of Knives* → Hits all enemies within range < 2.
   * Uses **energy** (regenerates per tick).

4. **Bonus (+5 pts): Hunter**

   * **Ability**: *Shoot* → Hits the closest enemy within range using arrows.
   * Uses **arrows** as a resource.

---

## 👾 Enemies

### General Properties

* Health
* Attack points
* Defense points
* Experience value (rewarded to player on kill)

### Types

1. **Monster**

   * Can move around the board.
   * Has **vision range** to chase player.
   * Otherwise moves randomly.

2. **Trap**

   * Cannot move.
   * Toggles visibility between *visible* and *invisible*.
   * Attacks player if within range < 2.

3. **Bonus (+5 pts): Boss**

   * Behaves like a monster.
   * Can also cast **special abilities** in combat.
   * Example Bosses:

     * **The Mountain** (vision range 6, frequency 5)
     * **Queen Cersei** (vision range 1, frequency 8)
     * **Night’s King** (vision range 8, frequency 3)

---

## ⚔️ Combat System

* Combat occurs when a unit tries to move into another unit’s tile.
* Steps:

  1. Attacker rolls attack (0 → attack points).
  2. Defender rolls defense (0 → defense points).
  3. If `attack > defense`, damage dealt = difference.
  4. If HP ≤ 0 → Unit dies.
* Rewards:

  * If enemy dies → player gains XP.
  * If player dies → tile marked with `X`, game over.

---

## 🖥️ CLI (Command Line Interface)

* Displays:

  * Board
  * Player stats
  * Combat info
  * Level-up notifications
* Controls:

  * `w` → Move up
  * `s` → Move down
  * `a` → Move left
  * `d` → Move right
  * `e` → Cast ability
  * `q` → Do nothing

---

## 📂 Input

* The program accepts a **directory path** as an argument.
* Directory contains files named `level<i>.txt` (e.g., `level1.txt`, `level2.txt`).
* Example board tiles:

  * `.` – Empty
  * `#` – Wall
  * `@` – Player
  * `X` – Dead player
  * Any other character = Enemy

---

## 🧑‍🤝‍🧑 Predefined Characters

### Players

#### Warriors

* Jon Snow → (HP 300, ATK 30, DEF 4, CD 3)
* The Hound → (HP 400, ATK 20, DEF 6, CD 5)

#### Mages

* Melisandre → (HP 100, ATK 5, DEF 1, Mana 300, Cost 30, Spell 15, Hits 5, Range 6)
* Thoros of Myr → (HP 250, ATK 25, DEF 4, Mana 150, Cost 20, Spell 20, Hits 3, Range 4)

#### Rogues

* Arya Stark → (HP 150, ATK 40, DEF 2, Cost 20)
* Bronn → (HP 250, ATK 35, DEF 3, Cost 50)

#### Hunter (Bonus)

* Ygritte → (HP 220, ATK 30, DEF 2, Range 6)

### Enemies

#### Monsters

* Lannister Soldier (`s`): HP 80, ATK 8, DEF 3, Vision 3, XP 25
* Lannister Knight (`k`): HP 200, ATK 14, DEF 8, Vision 4, XP 50
* Queen’s Guard (`q`): HP 400, ATK 20, DEF 15, Vision 5, XP 100
* Wright (`z`): HP 600, ATK 30, DEF 15, Vision 3, XP 100
* Bear-Wright (`b`): HP 1000, ATK 75, DEF 30, Vision 4, XP 250
* Giant-Wright (`g`): HP 1500, ATK 100, DEF 40, Vision 5, XP 500
* White Walker (`w`): HP 2000, ATK 150, DEF 50, Vision 6, XP 1000
* The Mountain (`M`): HP 1000, ATK 60, DEF 25, Vision 6, XP 500
* Queen Cersei (`C`): HP 100, ATK 10, DEF 10, Vision 1, XP 1000
* Night’s King (`K`): HP 5000, ATK 300, DEF 150, Vision 8, XP 5000

#### Traps

* Bonus Trap (`B`): HP 1, ATK 1, DEF 1, XP 250, Visible 1, Invisible 5
* Queen’s Trap (`Q`): HP 250, ATK 50, DEF 10, XP 100, Visible 3, Invisible 7
* Death Trap (`D`): HP 500, ATK 100, DEF 20, XP 250, Visible 1, Invisible 10

---

## 🧪 Testing

* Unit tests are provided.
* Covers basic and edge cases.
* Combat, movement, abilities, and leveling are tested.

---

## 🚀 How to Run

```bash
# Compile
javac -d out src/**/*.java

# Run 
java -cp out Game ./levels/
```

---

## ✨ Bonus Features Implemented

* Hunter class (Ygritte).
* Boss class (The Mountain, Cersei, Night’s King).


This project is part of an academic assignment and is intended for educational purposes only.

