<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width" />
  <title>Cryptomonnaie</title>
  <link rel="stylesheet" href="bootstrap-5.3.2-dist/css/bootstrap.min.css" />
  <link rel="stylesheet" href="assets/css/inter.css" />
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
    href="assets/css/core.min.css" />
  <link
    rel="stylesheet"
    href="assets/css/utilities.min.css" />

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
    src="assets/js/core.iife.js"></script>
  <script
    type="module"
    src="assets/js/icon.iife.js"></script>
  <script
    type="module"
    src="bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
</head>

<body class="bg-background text-foreground">
  <div class="">
    <div class="hidden md:block fixed z-20 inset-0 right-auto pb-10 pl-8 pr-6 overflow-y-auto">
      <div class="px-3 py-2">
        <h2 class="uk-h2 mb-4 ml-3 mt-3">Cryptomonnaie</h2>
        <h2 class="mb-2 px-3 text-lg font-semibold tracking-tight">Discover</h2>
        <ul class="uk-nav uk-nav-secondary">
          <li> <a class="font-medium" href="/"
              uk-toggle="" role="button"> <span class="mr-2 size-4"> <uk-icon icon="circle-play"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <circle cx="12" cy="12" r="10"></circle>
                    <polygon points="10 8 16 12 10 16 10 8"></polygon>
                  </svg></uk-icon> </span>
              Form
            </a> </li>
          <li> <a class="font-medium" href="/table" uk-toggle=""
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
              Table
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
              etatFond
            </a> </li>
        </ul>
      </div>
      <div class="px-3 py-2">
        <h2 class="mb-2 px-3 text-lg font-semibold tracking-tight">Library</h2>
        <ul class="uk-nav uk-nav-secondary">
          <li> <a class="font-medium" href="/ajoutFond" uk-toggle=""
              role="button"> <span class="mr-2 size-4"> <uk-icon icon="list-music"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <path d="M21 15V6"></path>
                    <path d="M18.5 18a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"></path>
                    <path d="M12 12H3"></path>
                    <path d="M16 6H3"></path>
                    <path d="M12 18H3"></path>
                  </svg></uk-icon> </span>
              depot/retrait
            </a> </li>
          <li> <a class="font-medium" href="https://examples.franken-ui.dev/examples/music#demo" uk-toggle=""
              role="button"> <span class="mr-2 size-4"> <uk-icon icon="user"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                  </svg></uk-icon> </span>
              Made for You
            </a> </li>
          <li> <a class="font-medium" href="https://examples.franken-ui.dev/examples/music#demo" uk-toggle=""
              role="button"> <span class="mr-2 size-4"> <uk-icon icon="mic-vocal"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <path d="m11 7.601-5.994 8.19a1 1 0 0 0 .1 1.298l.817.818a1 1 0 0 0 1.314.087L15.09 12"></path>
                    <path
                      d="M16.5 21.174C15.5 20.5 14.372 20 13 20c-2.058 0-3.928 2.356-6 2-2.072-.356-2.775-3.369-1.5-4.5">
                    </path>
                    <circle cx="16" cy="7" r="5"></circle>
                  </svg></uk-icon> </span>
              Artists
            </a> </li>
          <li> <a class="font-medium" href="https://examples.franken-ui.dev/examples/music#demo" uk-toggle=""
              role="button"> <span class="mr-2 size-4"> <uk-icon icon="library"><!----><svg
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="">
                    <path d="m16 6 4 14"></path>
                    <path d="M12 6v14"></path>
                    <path d="M8 8v12"></path>
                    <path d="M4 4v16"></path>
                  </svg></uk-icon> </span>
              Albums
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
      

      