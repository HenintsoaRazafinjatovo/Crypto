

<% String ctx=pageContext.getServletContext().getInitParameter("key"); %>

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
    __FRANKEN__.theme = "uk-theme-blue";
    if (
      __FRANKEN__.mode === "dark" ||
      (!__FRANKEN__.mode &&
        window.matchMedia("(prefers-color-scheme: dark)").matches)
    ) {
      htmlElement.classList.add("dark");
    } else {
      htmlElement.classList.remove("dark");
    }
    htmlElement.classList.add(__FRANKEN__.theme || "uk-theme-blue");
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
        <h2 class="uk-h2 mb-4 ml-3 mt-3">Backoffice crypto</h2>
        <ul class="uk-nav uk-nav-secondary">
          <li> <a class="font-medium" href="/porteFeuille"
              uk-toggle="" role="button"> <span class="mr-2 size-4"> <uk-icon icon="check"></uk-icon> </span>
              Validation depot & retrait
            </a> </li>
            <li> <a class="font-medium" href="/crypto/chart"
              uk-toggle="" role="button"> <span class="mr-2 size-4"> <uk-icon icon="arrow-left"></uk-icon> </span>
              Deconnexion
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
      

      