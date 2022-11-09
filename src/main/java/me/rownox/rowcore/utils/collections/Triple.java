package me.rownox.rowcore.utils.collections;

import lombok.Getter;

public class Triple <L, M, R> {
    @Getter
    L left;
    @Getter
    M middle;
    @Getter
    R right;

    private Triple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public static <L, M, R> Triple<L, M, R> of(L left, M middle, R right) {
        return new Triple<>(left, middle, right);
    }
}
