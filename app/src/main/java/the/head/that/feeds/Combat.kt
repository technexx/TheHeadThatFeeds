package the.head.that.feeds

import androidx.activity.viewModels

class Combat {
    var percentChanceOfGridAIAttack = 0
    var severityOfGridAIAttack = 0

    var hunterAttackStrength = 0
    var enemyAttackStrength = 0

    fun doesGridAIAttack(chance: Int) : Boolean { return chance >= (0..100).random() }

    fun doesPlayerWinCombat() : Boolean { return hunterAttackStrength >= enemyAttackStrength}
}