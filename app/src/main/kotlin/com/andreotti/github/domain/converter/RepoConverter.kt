package com.andreotti.github.domain.converter

import android.content.res.Resources
import com.andreotti.github.R
import com.andreotti.github.data.dto.RepoDTO
import com.andreotti.github.domain.vo.RepoVO
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

internal class RepoConverter(private val resources: Resources) {

    private companion object {
        val LOCATE = Locale("it", "IT")
        const val PATTERN = "#,###.##"
    }

    fun convert(data: RepoDTO): RepoVO {
        val stargazersAmount = data.stargazersCount.toFormat()
        val forksAmount = data.forksCount.toFormat()

        return RepoVO(
            nameRepo = data.fullNameRepo,
            username = data.owner.username,
            avatar = data.owner.avatarUrl,
            forksAmount = forksAmount,
            stargazersAmount = stargazersAmount,
            forksAmountDescription = resources.getString(
                R.string.accessibility_amount_forks, forksAmount
            ),
            stargazersAmountDescription = resources.getString(
                R.string.accessibility_amount_stargazers, stargazersAmount
            ),
            usernameDescription = resources.getString(
                R.string.accessibility_user, data.owner.username
            )
        )
    }

    private fun Number.toFormat() =
        DecimalFormat(
            PATTERN, DecimalFormatSymbols.getInstance(LOCATE)
        ).format(this)
}