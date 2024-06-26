{
  inputs = {
    # This must be the stable nixpkgs if you're running the app on a
    # stable NixOS install.  Mixing EGL library versions doesn't work.
    nixpkgs.url = "github:lorenzleutgeb/nixpkgs/gradle-8.8";
    build-gradle-application.url = "github:raphiz/buildGradleApplication";
    utils.url = "github:numtide/flake-utils";
    flake-compat = {
      url = github:edolstra/flake-compat;
      flake = false;
    };
  };

  outputs = inputs @ { self, nixpkgs, utils, build-gradle-application, ... }:
    utils.lib.eachDefaultSystem (system:
      let
        overlays = [build-gradle-application.overlays.default];
        pkgs = import nixpkgs {inherit system overlays;};
        version = self.shortRev or "dirty";
        jdk = pkgs.jdk22;
        update_action = "build";
        gradle = (pkgs.callPackage pkgs.gradle-packages.gradle_8 { 
	           java = jdk;
         });
        libPath = with pkgs; lib.makeLibraryPath [
          libGL
          libxkbcommon
          wayland
          xorg.libX11
          xorg.libXcursor
          xorg.libXi
          xorg.libXrandr
          xorg.libXxf86vm
          xorg.libXtst
          fontconfig
        ];
      in
      {
        defaultPackage = pkgs.callPackage ./package.nix {
            inherit version gradle jdk;
        };

        devShell = with pkgs;
          mkShellNoCC {
            buildInputs = [jdk openjfx22 gradle xorg.libxcb (updateVerificationMetadata.override { updateAction = update_action; })];
            LD_LIBRARY_PATH = libPath;
          };
      });
}
