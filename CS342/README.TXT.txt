#Merchant menu works for older file but should not
#Makefile will be resent
GROUP 1 
Members
Alexander Adrahtas aadrah2
Manuel Torres mtorre66
Maurice Williams mwilli96
--Skip down to the bottom to see basic instructions on playing the game
Three new extensions where implemented 

Battle- a player vs player, player vs npc, turn based battle mode 

Weapon- Artifact class child for battling

Merchant- merchants for buying and selling items 


*********BATTLE*********
Implemented By Alexander Adrahtas 

Classes included from project 3:
Character, Decesion Maker, Game, GameTester
Classes Added:
Battle 

A battle is triggered when two Characters are in the same room. A battle can not be accepted or decline you must fight.
Loser of battle is out of the game and drop their inventory.

To go straight to a battle for testing type BATTLE, battle, b, or Battle.

Once in Battle you are allowed four moves:

StrongAttack MediumAttack WeakAttack Defend

Figthting is turn based each player enters their move and then an outcome is determined from it.

Each attack takes away stamina, and Defending gains you stamina and lessens the damage dealt if opponent decides to attack
If you run out of stamina you are not allowed a next turn because you need to rest so be careful.

The stronger the attack you choose the more stamina you loose but your chance at dealing more damage increases.

*********Weapons*********
Implemented by Manuel Torres
Classes included from project 3:
Artifact, Move
Classes extended:
Artifact

A weapon inherites from artifact and are used in battle
When a battle starts you are asked from your list of weapons to pick one and use

A weapon has two stats defense and attack that varies between weapons. Attack helps you deal damage, while Defense helps you
lessen damage taken by opposing attacks.

Weapons can be found in the game scattered through the rooms our bought through from a merchant. Only one weapon can be used
during battle

********Merchant*********
Implemented by Maurice Williams
Classes included from project 3:
Direction Places
Classes added: 
Money, Merchant, Currency

The money class inherits from Currency. For right now I'm having money as the only form of currency but in the
future we may add more. The money is supposed to be used by the players to interact with the merchants. In order to
interact with merchant, just type 'merchant' or 'm' on the menu screen and it's pretty self explanatory. You get a print out
of the items and you get to see how much money you have after each transaction. Name does have to be case sensitive for this one
since it's hased by string. You can buy or sell items with the merchant and view their inventory



How to play the game:

There are the usual commands from the earlier projects and now there are new commands: merchant or battle, there also keyboard
shortcuts you can do: just type m for merchant or b for battle. When you type merchant, you're taken to the merchant menu.
When you type battle, you can select two fighter names and then you can pick a weapon out of the standard list to fight with
Details for how to fight are up at the top of the readme file. Also, there are shortcuts when battling you can enter
s for strong attack, w for weak, m for medium, d for defend, r for run. 

