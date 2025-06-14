# Text Battle RPG

This is a game called Dungeon of the Dead Turtles. It is a game where the player is stuck inside a dungeon and forced to fight monsters. There are dynamic fights with options at every move and many ways to play the game. The game is turn-based and requires text input for every move.
## Features

- Multiple playable classes: Warrior, Mage, Rogue, etc.
- Diverse enemies: Zombies, Giants, Witches, ect
- Item usage and basic inventory system
- Battle system with randomized outcomes for certain events which influence strategy
- Modular design for easy expansion on all fronts

## 📁 Project Structure

```
Text-Battle-RPG/
├── src/
│   └── game/
│       └── textbasedRPG/
│           ├── GameRunner.java
│           ├── Battle.java
│           ├── Item.java
│           ├── entityclasses/
│           │   ├── Entity.java
│           │   ├── playerclasses/
│           │   │   ├── Player.java
│           │   │   └── [other class files...]
│           │   └── monsterclasses/
│           │       ├── Monster.java
│           │       └── [other class files...]
│           └── [other class files...]
├── bin/ # (Ignored in Git)
├── module-info.java
├── .gitignore
└── README.md
```

### Requirements To Run
- Java 12+
- Any Java-supporting IDE

### License

This project is licensed under the [CC BY-NC 4.0 License](https://creativecommons.org/licenses/by-nc/4.0/).

You may use, modify, and run this code for personal and non-commercial purposes only. Attribution to the original author [@TimuridEmpire](https://github.com/TimuridEmpire) is required.


