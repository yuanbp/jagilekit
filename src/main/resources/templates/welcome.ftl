<#include "common/_layout.ftl">
<@layout page_tab="main_index">
    <@shiro.authenticated>
        welcome
    </@shiro.authenticated>
</@layout>