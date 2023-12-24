package dev.ngdangkiet.dropbox;

/**
 * @author ngdangkiet
 * @since 12/23/2023
 */

@FunctionalInterface
interface DropboxActionResolver<T> {

    T execute();
}
