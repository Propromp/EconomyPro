# EconomyPro
super-useful economy plugin
## features
- Full Vault support
- simple and useful API
- database support(Not implemented yet)
- no junks. just economy.
## commands
- /money:check your balance
- /bank:manage your bank account

    - /bank help:shows help
    - /bank select [account]:select the account as vault-linked account
    - /bank info [account]:shows the information of the bank account
    - /bank delete [account]:delete the bank account
    - /bank create [account]:create the bank account
    - /bank invite [account] [player]:invite [player] to [account]
    - /bank leave [account]:leave [account]
    - /bank join [account]:join to [account]
- /balance:manage balance
  
    - /balance help:shows help
    - /balance set [player] [amount]:set the balance of [player] to the [amount]
    - /balance get [player]: get the balance of [player]
    - /balance deposit [player] [amount]:deposit [amount] to [player]
    - /balance withdraw [player] [amount]:withdraw [amount] from [player]
- /pay [player] [amount]:pay [player] [amount]
- /economypro:shows help of this plugin
- /balancetop:displays Top 10
## permissions
- economypro.*

    default: op
- economypro.money
    
    default: all players
- economypro.bank
    
    default: all players
- economypro.balance.set
    
    default: op
- economypro.balance.get
    
    default: all players
- economypro.pay
    
    default: all players
- economypro.economypro
    
    default: all players
- economypro.balancetop
    
    default: all players
