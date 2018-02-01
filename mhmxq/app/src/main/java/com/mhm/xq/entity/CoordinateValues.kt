package com.mhm.xq.entity

class CoordinateValues {

    private var leftOnAngle: CoordinatesXY? = null
    private var rightOnAngle: CoordinatesXY? = null
    private var leftUnderAngle: CoordinatesXY? = null
    private var rightUnderAngle: CoordinatesXY? = null

    public fun setLeftOnAngle(leftOnAngle: CoordinatesXY) {
        this.leftOnAngle = leftOnAngle
    }

    public fun getLeftOnAngle(): CoordinatesXY? = this.leftOnAngle

    public fun setRightOnAngle(rightOnAngle: CoordinatesXY) {
        this.rightOnAngle = rightOnAngle
    }

    public fun getRightOnAngle(): CoordinatesXY? = this.rightOnAngle

    public fun setLeftUnderAngle(leftUnderAngle: CoordinatesXY) {
        this.leftUnderAngle = leftUnderAngle
    }

    public fun getLeftUnderAngle(): CoordinatesXY? = this.leftUnderAngle

    public fun setRightUnderAngle(rightUnderAngle: CoordinatesXY) {
        this.rightUnderAngle = rightUnderAngle
    }

    public fun getRightUnderAngle(): CoordinatesXY? = this.rightUnderAngle
}