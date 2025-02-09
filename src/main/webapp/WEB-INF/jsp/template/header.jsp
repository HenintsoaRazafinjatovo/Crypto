<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width" />
  <title>Cryptomonnaie</title>
  <link rel="stylesheet" href="/bootstrap-5.3.2-dist/css/bootstrap.min.css" />
  <link rel="stylesheet" href="/assets/css/inter.css" />
  <style>
    :root {
      font-family: Inter, sans-serif;
      font-feature-settings: "liga" 1, "calt" 1;
      /* fix for Chrome */
    }

    @supports (font-variation-settings: normal) {
      :root {
        font-family: InterVariable, sans-serif;
      }
    }
  </style>
  <link
    rel="stylesheet"
    href="/assets/css/core.min.css" />
  <link
    rel="stylesheet"
    href="/assets/css/utilities.min.css" />

  <script>
    const htmlElement = document.documentElement;
    const __FRANKEN__ = JSON.parse(
      localStorage.getItem("__FRANKEN__") || "{}"
    );
    if (
      __FRANKEN__.mode === "dark" ||
      (!__FRANKEN__.mode &&
        window.matchMedia("(prefers-color-scheme: dark)").matches)
    ) {
      htmlElement.classList.add("dark");
    } else {
      htmlElement.classList.remove("dark");
    }
    htmlElement.classList.add(__FRANKEN__.theme || "uk-theme-zinc");
    htmlElement.classList.add(__FRANKEN__.radii || "uk-radii-md");
    htmlElement.classList.add(__FRANKEN__.shadows || "uk-shadows-sm");
    htmlElement.classList.add(__FRANKEN__.font || "uk-font-sm");
  </script>

  <script
    type="module"
    src="/assets/js/core.iife.js"></script>
  <script
    type="module"
    src="/assets/js/icon.iife.js"></script>
  <script
    type="module"
    src="/bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
</head>

<body class="bg-background text-foreground">
  <div class="">
    <div class="hidden md:block fixed z-20 inset-0 right-auto pb-10 pl-8 pr-6 overflow-y-auto">
      <div class="px-3 py-2">
        <h2 class="uk-h2 mb-4 ml-3 mt-3">Cryptomonnaie</h2>
        <ul class="uk-nav uk-nav-secondary">
          <li> <a class="font-medium" href="/porteFeuille"
              uk-toggle="" role="button"> <span class="mr-2 size-4"> <uk-icon icon="tag"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <circle cx="12" cy="12" r="10"></circle>
                    <polygon points="10 8 16 12 10 16 10 8"></polygon>
                  </svg></uk-icon> </span>
              Portefeuille
            </a> </li>
            <li> <a class="font-medium" href="/crypto/chart"
              uk-toggle="" role="button"> <span class="mr-2 size-4"> <uk-icon icon="circle-play"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <circle cx="12" cy="12" r="10"></circle>
                    <polygon points="10 8 16 12 10 16 10 8"></polygon>
                  </svg></uk-icon> </span>
              Crypto et cours
            </a> </li>
            <li> <a class="font-medium" href="/etatFond" uk-toggle=""
              role="button"> <span class="mr-2 size-4"> <uk-icon icon="radio"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <path d="M4.9 19.1C1 15.2 1 8.8 4.9 4.9"></path>
                    <path d="M7.8 16.2c-2.3-2.3-2.3-6.1 0-8.5"></path>
                    <circle cx="12" cy="12" r="2"></circle>
                    <path d="M16.2 7.8c2.3 2.3 2.3 6.1 0 8.5"></path>
                    <path d="M19.1 4.9C23 8.8 23 15.1 19.1 19"></path>
                  </svg></uk-icon> </span>
              Etat fond
            </a> </li>
        </ul>
      </div>
      <div class="px-3 py-2">
        <h2 class="mb-2 px-3 text-lg font-semibold tracking-tight">depot ou retrait</h2>
        <ul class="uk-nav uk-nav-secondary">
          <li> <a class="font-medium" href="/ajoutFond" uk-toggle=""
              role="button"> <span class="mr-2 size-4"> <uk-icon icon="list-music"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <path d="M4.9 19.1C1 15.2 1 8.8 4.9 4.9"></path>
                    <path d="M7.8 16.2c-2.3-2.3-2.3-6.1 0-8.5"></path>
                    <circle cx="12" cy="12" r="2"></circle>
                    <path d="M16.2 7.8c2.3 2.3 2.3 6.1 0 8.5"></path>
                    <path d="M19.1 4.9C23 8.8 23 15.1 19.1 19"></path>
                  </svg></uk-icon> </span>
              Depot et Retrait
            </a> </li>
          <li> 
            <a class="font-medium" href="/analyse" uk-toggle=""
              role="button"> <span class="mr-2 size-4"> <uk-icon icon="user"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                  </svg></uk-icon> </span>
              analyse
            </a> 
          </li>
            <li> <a class="font-medium" href="/analyseResult" uk-toggle=""
              role="button"> <span class="mr-2 size-4"> <uk-icon icon="binoculars"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <path d="M10 10h4"></path>
                    <path d="M19 7V4a1 1 0 0 0-1-1h-2a1 1 0 0 0-1 1v3"></path>
                    <path
                      d="M20 21a2 2 0 0 0 2-2v-3.851c0-1.39-2-2.962-2-4.829V8a1 1 0 0 0-1-1h-4a1 1 0 0 0-1 1v11a2 2 0 0 0 2 2z">
                    </path>
                    <path d="M 22 16 L 2 16"></path>
                    <path
                      d="M4 21a2 2 0 0 1-2-2v-3.851c0-1.39 2-2.962 2-4.829V8a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v11a2 2 0 0 1-2 2z">
                    </path>
                    <path d="M9 7V4a1 1 0 0 0-1-1H6a1 1 0 0 0-1 1v3"></path>
                  </svg></uk-icon> </span>
              resultat analyse
            </a> </li>
            <li> <a class="font-medium" href="/commissionAnalyse"
              uk-toggle="" role="button"> <span class="mr-2 size-4"> <uk-icon icon="circle-play"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <circle cx="12" cy="12" r="10"></circle>
                    <polygon points="10 8 16 12 10 16 10 8"></polygon>
                  </svg></uk-icon> </span>
              Form
            </a> </li>
                <li> <a class="font-medium" href="/commissionConfig"
              uk-toggle="" role="button"> <span class="mr-2 size-4"> <uk-icon icon="circle-play"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <circle cx="12" cy="12" r="10"></circle>
                    <polygon points="10 8 16 12 10 16 10 8"></polygon>
                  </svg></uk-icon> </span>
              updateConfig
            </a> </li>
        </ul>
       
      </div>
    </div>
    <div class="custom-padding" style="margin:50px;">
      <style>
        .custom-padding {
          padding-left: 19.5rem;
        }

        @media (max-width: 768px) {

          /* Bootstrap's md breakpoint is 768px */
          .custom-padding {
            padding-left: 0;
          }
        }
      </style>
      

      