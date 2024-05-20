package com.example.lifebalance.screens.expense

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Outlined.ArrowDownward: ImageVector
    get() {
        if (_arrowDownward != null) {
            return _arrowDownward!!
        }
        _arrowDownward = materialIcon(name = "Outlined.ArrowDownward") {
            materialPath {
                moveTo(20.0f, 12.0f)
                lineToRelative(-1.41f, -1.41f)
                lineTo(13.0f, 16.17f)
                verticalLineTo(4.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(12.17f)
                lineToRelative(-5.58f, -5.59f)
                lineTo(4.0f, 12.0f)
                lineToRelative(8.0f, 8.0f)
                lineToRelative(8.0f, -8.0f)
                close()
            }
        }
        return _arrowDownward!!
    }

private var _arrowDownward: ImageVector? = null

public val Icons.Outlined.ArrowUpward: ImageVector
    get() {
        if (_arrowUpward != null) {
            return _arrowUpward!!
        }
        _arrowUpward = materialIcon(name = "Outlined.ArrowUpward") {
            materialPath {
                moveTo(4.0f, 12.0f)
                lineToRelative(1.41f, 1.41f)
                lineTo(11.0f, 7.83f)
                verticalLineTo(20.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(7.83f)
                lineToRelative(5.58f, 5.59f)
                lineTo(20.0f, 12.0f)
                lineToRelative(-8.0f, -8.0f)
                lineToRelative(-8.0f, 8.0f)
                close()
            }
        }
        return _arrowUpward!!
    }

private var _arrowUpward: ImageVector? = null

public val Icons.Outlined.NorthEast: ImageVector
    get() {
        if (_northEast != null) {
            return _northEast!!
        }
        _northEast = materialIcon(name = "Outlined.NorthEast") {
            materialPath {
                moveTo(9.0f, 5.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(6.59f)
                lineTo(4.0f, 18.59f)
                lineTo(5.41f, 20.0f)
                lineTo(17.0f, 8.41f)
                verticalLineTo(15.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(5.0f)
                horizontalLineTo(9.0f)
                close()
            }
        }
        return _northEast!!
    }

private var _northEast: ImageVector? = null

public val Icons.Outlined.SouthWest: ImageVector
    get() {
        if (_southWest != null) {
            return _southWest!!
        }
        _southWest = materialIcon(name = "Outlined.SouthWest") {
            materialPath {
                moveTo(15.0f, 19.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineTo(8.41f)
                lineTo(20.0f, 5.41f)
                lineTo(18.59f, 4.0f)
                lineTo(7.0f, 15.59f)
                verticalLineTo(9.0f)
                horizontalLineTo(5.0f)
                verticalLineToRelative(10.0f)
                horizontalLineTo(15.0f)
                close()
            }
        }
        return _southWest!!
    }

private var _southWest: ImageVector? = null